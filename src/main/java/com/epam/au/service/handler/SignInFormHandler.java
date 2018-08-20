package com.epam.au.service.handler;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.impl.UserDataBaseDAO;
import com.epam.au.entity.Role;
import com.epam.au.entity.User;
import com.epam.au.entity.UserStatus;
import org.apache.log4j.Logger;
import com.epam.au.service.util.PasswordReformer;
import com.epam.au.service.validator.Errors;
import com.epam.au.service.validator.SignInUserValidator;
import com.epam.au.service.validator.SignupUserValidator;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInFormHandler implements FormHandler {
    private final Logger LOG = Logger.getLogger(SignInFormHandler.class);

    private SignInUserValidator validator;
    private UserDataBaseDAO dao;
    private Errors errors;

    public SignInFormHandler() {
        validator = new SignInUserValidator();
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (UserDataBaseDAO) factory.create("user");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    @Override
    public boolean handle(HttpWrapper wrapper) {
        User user = new User();

        user.setEmail(wrapper.getRequestParameter("email"));
        user.setPassword(wrapper.getRequestParameter("password"));

        if (validator.validate(user)) {
            user = dao.findByEmail(user.getEmail());
            wrapper.addSessionAttribute("user", user);

            return true;
        } else {
            errors = validator.getErrors();

            return false;
        }
    }

    public Errors getErrors() {
        return errors;
    }
}
