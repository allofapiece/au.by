package service.handler;

import controller.command.Command;
import controller.command.CommandProvider;
import controller.command.IllegalCommandException;
import dao.AbstractFactory;
import dao.DataBaseDAO;
import dao.DataBaseDAOFactory;
import dao.Factory;
import dao.exception.DAOException;
import dao.exception.IllegalDAOTypeException;
import dao.impl.BankAccountDataBaseDAO;
import dao.impl.UserDataBaseDAO;
import entity.Account;
import entity.Role;
import entity.User;
import entity.UserStatus;
import org.apache.log4j.Logger;
import service.util.PasswordReformer;
import service.validator.ConnectBankAccountValidator;
import service.validator.Errors;
import service.validator.SignupUserValidator;
import service.wrapper.HttpWrapper;

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
            LOG.error("dao error", e);
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
