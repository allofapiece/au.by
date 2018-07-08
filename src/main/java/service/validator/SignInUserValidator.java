package service.validator;

import dao.impl.UserDataBaseDAO;
import entity.User;
import service.util.PasswordReformer;

public class SignInUserValidator extends Validator {
    private UserDataBaseDAO dao = new UserDataBaseDAO();
    private PasswordReformer reformer = new PasswordReformer();

    @Override
    public boolean validate(Object object) {
        User user = (User) object;

        emailValidate(user.getEmail(), "user.field.email");
        passwordValidate(user.getPassword(), "user.field.password");

        if (!isValid()) {
            return false;
        }

        User targetUser = dao.findCredentialsByEmail(user.getEmail());

        if (
                targetUser == null ||
                !targetUser.getPassword().equals(reformer.reform(user.getPassword(), user))
        ) {
            getErrors().addFieldError("user.field.all", "Credentials are not valid");
            setValid(false);

            return isValid();
        }

        return isValid();
    }


}
