package service.validator;

import dao.DataBaseDAOFactory;
import dao.exception.IllegalDataBaseDAOException;
import dao.impl.UserDataBaseDAO;
import entity.User;
import org.apache.log4j.Logger;

public class SignupUserValidator extends Validator {
    private static final Logger LOG = Logger.getLogger(SignupUserValidator.class);
    private UserDataBaseDAO dao;

    public SignupUserValidator() {
        DataBaseDAOFactory daoFactory = new DataBaseDAOFactory();
        try {
            dao = (UserDataBaseDAO) daoFactory.create("user");
        } catch (IllegalDataBaseDAOException e) {
            LOG.error(e);
        }
    }

    @Override
    public boolean validate(Object object) {
        User user = (User) object;

        emailValidate(user.getEmail(), "user.field.email");

        passwordValidate(user.getPassword(), "user.field.password");
        passwordConfirmationValidate(
                user.getPassword(),
                user.getConfirmedPassword(),
                "user.field.confirmation"
        );

        emptyValidate(user.getName(), "user.field.name", true);
        stringSizeValidate(user.getName(), 2, 30, "user.field.name", false);
        stringPatternMatchingValidate(
                user.getName(),
                regexps.getString("validation.user.name"),
                "user.field.name",
                false
        );

        emptyValidate(user.getSurname(), "user.field.surname", true);
        stringSizeValidate(user.getSurname(), 2, 30, "user.field.surname", false);
        stringPatternMatchingValidate(
                user.getSurname(),
                regexps.getString("validation.user.surname"),
                "user.field.surname",
                false
        );

        if (isValid() && !uniqueEmailValidate(user.getEmail())) {
            getErrors().addFieldError("user.field.email", "error.exists");
            setValid(false);
        }

        return isValid();
    }

    private boolean uniqueEmailValidate(String email) {
        return this.dao.amountByEmail(email) == 0;
    }
}
