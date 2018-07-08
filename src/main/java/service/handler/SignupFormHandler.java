package service.handler;

import dao.AbstractFactory;
import dao.DataBaseDAO;
import dao.DataBaseDAOFactory;
import dao.Factory;
import dao.exception.DAOException;
import dao.exception.IllegalDAOTypeException;
import dao.impl.UserDataBaseDAO;
import entity.Role;
import entity.User;
import entity.UserStatus;
import org.apache.log4j.Logger;
import service.util.PasswordReformer;
import service.validator.Errors;
import service.validator.SignupUserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignupFormHandler implements FormHandler {
    private final Logger LOG = Logger.getLogger(SignupFormHandler.class);

    private SignupUserValidator validator;
    private UserDataBaseDAO dao;
    private Errors errors;

    public SignupFormHandler() {
        validator = new SignupUserValidator();
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (UserDataBaseDAO) factory.create("user");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    @Override
    public boolean handle(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User();
        PasswordReformer reformer = new PasswordReformer();

        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        user.setConfirmedPassword(req.getParameter("confirm-password"));
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));

        if (validator.validate(user)) {
            user.setStatus(UserStatus.DISABLED);
            user.setPassword(reformer.reform(user.getPassword(), user));
            user.addRole(Role.USER);

            dao.create(user);

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
