package com.epam.au.service.validator;

import com.epam.au.bundle.BundleNamesStore;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validator {
    private Errors errors;
    private boolean isValid = true;
    protected ResourceBundle regexps = ResourceBundle.getBundle(BundleNamesStore.EXPRESSION_BUNDLE);

    public Validator() {
        errors = new Errors();
    }

    public Validator(Errors errors) {
        this.errors = errors;
    }

    abstract boolean validate(Object object);

    public boolean validate(Object object, Errors errors) {
        this.errors = errors;
        return validate(object);
    }

    boolean emptyValidate(Object obj, String field, boolean isStacked) {
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

    boolean emptyValidate(String string, String field, boolean isStacked) {
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

    boolean stringSizeValidate(
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

    boolean stringPatternMatchingValidate(
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

    boolean emailValidate(
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

    boolean passwordValidate(
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

    boolean passwordConfirmationValidate(
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
