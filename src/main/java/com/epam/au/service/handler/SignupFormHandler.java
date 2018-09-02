package com.epam.au.service.handler;

import com.epam.au.controller.command.Command;
import com.epam.au.controller.command.CommandProvider;
import com.epam.au.controller.command.IllegalCommandException;
import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAO;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.Factory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.IllegalDAOTypeException;
import com.epam.au.dao.impl.BankAccountDataBaseDAO;
import com.epam.au.dao.impl.UserDataBaseDAO;
import com.epam.au.entity.Account;
import com.epam.au.entity.Role;
import com.epam.au.entity.User;
import com.epam.au.entity.UserStatus;
import org.apache.log4j.Logger;
import com.epam.au.service.util.PasswordReformer;
import com.epam.au.service.validator.ConnectBankAccountValidator;
import com.epam.au.service.validator.Errors;
import com.epam.au.service.validator.SignupUserValidator;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignupFormHandler implements FormHandler {
    private final Logger LOG = Logger.getLogger(SignupFormHandler.class);

    private SignupUserValidator validator;
    private ConnectBankAccountValidator accountValidator;
    private UserDataBaseDAO dao;
    private Errors errors;

    public SignupFormHandler() {
        validator = new SignupUserValidator();
        accountValidator = new ConnectBankAccountValidator();
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (UserDataBaseDAO) factory.create("user");
        } catch (DAOException e) {
            LOG.error("com.epam.au.dao error", e);
        }
    }

    @Override
    public boolean handle(HttpWrapper wrapper) {
        User user = new User();
        this.errors = new Errors();

        PasswordReformer reformer = new PasswordReformer();

        user.setEmail(wrapper.getRequestParameter("email"));
        user.setPassword(wrapper.getRequestParameter("password"));
        user.setConfirmedPassword(wrapper.getRequestParameter("confirm-password"));
        user.setName(wrapper.getRequestParameter("name"));
        user.setSurname(wrapper.getRequestParameter("surname"));

        if (validator.validate(user)) {
            user.setStatus(UserStatus.DISABLED);
            user.setPassword(reformer.reform(user.getPassword(), user));
            user.addRole(Role.USER);

            dao.create(user);

            wrapper.addSessionAttribute("user", user);
            
            if (wrapper.hasRequestAttribute("connect-account") && wrapper.getBoolean("connect-account")) {
                try {
                    Command connectAccount = new CommandProvider().getCommand("account-connect");
                    wrapper.addSessionAttribute("user", user);
                    connectAccount.execute(wrapper);
                } catch (IllegalCommandException e) {
                    LOG.error("Undefined command", e);
                }
            }

            return true;
        } else {
            this.errors = validator.getErrors();
            wrapper.addErrors(errors);
            return false;
        }
    }

    public Errors getErrors() {
        return errors;
    }
}
