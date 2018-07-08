package service.validator;

import entity.User;

public class SignupUserValidator extends Validator {
    @Override
    public boolean validate(Object object) {
        User user = (User) object;

        emailValidate(user.getEmail(), "user.field.email");

        passwordValidate(user.getPassword(), "user.field.password");
        passwordConfirmationValidate(
                user.getPassword(),
                user.getConfirmedPassword(),
                "user.field.password"
        );

        stringSizeValidate(user.getName(), 2, 30, "user.field.name", true);
        stringPatternMatchingValidate(
                user.getName(),
                regexps.getString("validation.user.name"),
                "user.field.name",
                false
        );

        stringSizeValidate(user.getSurname(), 2, 30, "user.field.surname", true);
        stringPatternMatchingValidate(
                user.getSurname(),
                regexps.getString("validation.user.surname"),
                "user.field.surname",
                false
        );

        return isValid();
    }


}
