package service.handler;

import dao.AbstractFactory;
import dao.DataBaseDAOFactory;
import dao.exception.DAOException;
import dao.impl.UserDataBaseDAO;
import entity.Role;
import entity.User;
import entity.UserStatus;
import org.apache.log4j.Logger;
import service.util.PasswordReformer;
import service.validator.Errors;
import service.validator.SignInUserValidator;
import service.validator.SignupUserValidator;

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
    public boolean handle(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User();

        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));

        if (validator.validate(user)) {
            user = dao.findByEmail(user.getEmail());
            req.setAttribute("user", user);

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
