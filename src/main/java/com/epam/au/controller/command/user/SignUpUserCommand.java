package com.epam.au.controller.command.user;

import com.epam.au.controller.ResponseInfo;
import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.handler.SignupFormHandler;
import com.epam.au.service.wrapper.HttpWrapper;

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
            wrapper.setPage("other.main");
            return wrapper;
        }*/

        if (wrapper.getMethod().equals("GET")) {
            wrapper.setPage("user.signup");
        } else {
            if (formHandler.handle(wrapper)) {
                wrapper.setIsUpdated(true);

                if (wrapper.getErrors().hasErrors()) {
                    wrapper.setPage("account.connect");
                    wrapper.addError("page", "warning.account.signup_error");
                } else {
                    wrapper.setPage("other.main");
                }
            } else {
                wrapper.setPage("user.signup");
            }
        }

        return wrapper;
    }
}
