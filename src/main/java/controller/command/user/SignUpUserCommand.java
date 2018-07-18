package controller.command.user;

import controller.ResponseInfo;
import controller.command.Command;
import entity.User;
import service.handler.SignupFormHandler;
import service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignUpUserCommand implements Command {
    private SignupFormHandler formHandler;

    public SignUpUserCommand() {
        formHandler = new SignupFormHandler();
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        User user = new User();

        /*if (wrapper.getSessionAttribute("user") != null) {
            wrapper.setIsUpdated(true);
            wrapper.setPage("jsp/main.jsp");
            return wrapper;
        }*/

        if (wrapper.getMethod().equals("GET")) {
            wrapper.setPage("jsp/user/signup.jsp");
        } else {
            if (formHandler.handle(wrapper)) {
                wrapper.setIsUpdated(true);



                if (wrapper.getErrors().hasErrors()) {
                    wrapper.setPage("jsp/account/connect.jsp");
                    wrapper.addError("account.page", "warning.signup_error");
                } else {
                    wrapper.setPage("jsp/main.jsp");
                }
            } else {
                wrapper.setPage("jsp/user/signup.jsp");
            }
        }

        return wrapper;
    }
}
