package com.epam.au.service.validator;

import com.epam.au.bundle.BundleNamesStore;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.IllegalDataBaseDAOException;
import com.epam.au.dao.impl.UserDataBaseDAO;
import com.epam.au.entity.User;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO create the validation for type of value(int, string, double, etc.)
public abstract class Validator {
    private static final Logger LOG = Logger.getLogger(Validator.class);
    private Errors errors;
    private boolean isValid = true;
    private UserDataBaseDAO userDAO;

    protected ResourceBundle regexps = ResourceBundle.getBundle(BundleNamesStore.EXPRESSION_BUNDLE);

    public Validator() {
        errors = new Errors();
        DataBaseDAOFactory daoFactory = new DataBaseDAOFactory();
        try {
            userDAO = (UserDataBaseDAO) daoFactory.create("user");
        } catch (IllegalDataBaseDAOException e) {
            LOG.error(e);
        }
    }

    public Validator(Errors errors) {
        this();
        this.errors = errors;
    }

    public abstract boolean validate(Object object);

    public boolean validate(Object object, Errors errors) {
        this.errors = errors;
        return validate(object);
    }

    public boolean emptyValidate(Object obj, String field, boolean isStacked) {
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && messages != null) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (obj == null) {
            messages.add("error.empty");
            isValid = false;
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean emptyValidate(String string, String field, boolean isStacked) {
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && messages != null) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (string == null || string.trim().equals("")) {
            messages.add("error.empty");
            isValid = false;
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean emptyValidate(double value, String field, boolean isStacked, double[] ... conditions) {
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && messages != null) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (conditions.length == 0) {
            if (value == 0) {
                messages.add("error.empty");
                isValid = false;
            } else {
                return true;
            }
        } else {
             if (Arrays.asList(conditions).contains(value)) {
                 messages.add("error.empty");
                 isValid = false;
             } else {
                 return true;
             }
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean stringSizeValidate(
            String value,
            int min,
            int max,
            String field,
            boolean isStacked
    ) {
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && messages != null) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (value.length() < min || value.length() > max) {
            messages.add("error.length");
            isValid = false;
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean numberSizeValidate(
            double value,
            double max,
            String field,
            boolean isStacked
    ) {
        return numberSizeValidate(value, 0, max, field, isStacked);
    }

    public boolean numberSizeValidate(
            double value,
            double min,
            double max,
            String field,
            boolean isStacked
    ) {
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && messages != null) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (value < min || value > max) {
            messages.add("error.length");
            isValid = false;
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean stringPatternMatchingValidate(
            String value,
            String patternString,
            String field,
            boolean isStacked
    ) {
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && errors.getAllErrors().containsKey(field)) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(value);

        if (!matcher.matches()) {
            messages.add("error.pattern");
            isValid = false;
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean emailValidate(
            String email,
            String field
    ) {
        emptyValidate(email, field, true);
        stringSizeValidate(email, 4, 50, field, false);
        stringPatternMatchingValidate(
                email,
                regexps.getString("validation.email"),
                field,
                false
        );

        return isValid;
    }

    public boolean passwordValidate(
            String password,
            String field
    ) {
        emptyValidate(password, field, true);
        stringSizeValidate(password, 6, 18, field, false);
        stringPatternMatchingValidate(
                password,
                regexps.getString("validation.user.password"),
                field,
                false
        );

        return isValid;
    }

    public boolean passwordConfirmationValidate(
            String password,
            String confirmedPassword,
            String field
    ) {
        emptyValidate(confirmedPassword, field, true);
        List<String> messages = errors.getFieldErrors(field);

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (!password.equals(confirmedPassword)) {
            messages.add("error.confirmation");
            isValid = false;
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean userExistValidate(User user, String field) {
        return userExistValidate(user.getId(), field);
    }

    public boolean userExistValidate(long id, String field) {
        boolean v = true;
        List<String> messages = errors.getFieldErrors(field);
        User user = null;

        if (messages == null) {
            messages = new ArrayList<>();
        }

        try {
            user = userDAO.find(id);
            if (user == null) {
                messages.add("error.nexists");
                v = false;
                setValid(v);
            }
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }

        return v;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
