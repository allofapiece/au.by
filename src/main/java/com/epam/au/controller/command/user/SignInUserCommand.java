package com.epam.au.controller.command.user;

import com.epam.au.controller.ResponseInfo;
import com.epam.au.controller.Router;
import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.handler.SignInFormHandler;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignInUserCommand implements Command {
    private SignInFormHandler formHandler;

    public SignInUserCommand() {
        formHandler = new SignInFormHandler();
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        if (wrapper.getSessionAttribute("user") != null) {
            wrapper.setPage("other.main");
            wrapper.setIsUpdated(true);
            return wrapper;
        }

        User user = new User();

        if (wrapper.getMethod().equals("GET")) {
            wrapper.setPage("user.login");
        } else {
            if (formHandler.handle(wrapper)) {
                //session.setAttribute("isRemembered", req.getParameter("rememberme"));
                wrapper.setIsUpdated(true);
                wrapper.setPage("other.main");
            } else {
                wrapper.addRequestAttribute("errors", formHandler.getErrors().getAllErrors());
                wrapper.setPage("user.login");
            }
        }

        return wrapper;
    }
}
