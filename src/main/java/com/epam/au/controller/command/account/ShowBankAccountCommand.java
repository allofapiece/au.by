package com.epam.au.controller.command.account;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletResponse;

public class ShowBankAccountCommand implements Command {

    public ShowBankAccountCommand() {
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        User user = (User) wrapper.getSessionAttribute("user");

        if (user == null) {
            wrapper.setPage("jsp/user/signin.jsp");
            wrapper.setTitle("Sign in");
            wrapper.setIsUpdated(true);
            wrapper.addError("signin.page", "warning.permission");
            return wrapper;
        }

        if (user.getAccount() == null) {
            wrapper.setPage("jsp/account/connect.jsp");
            wrapper.setIsUpdated(true);
            wrapper.addError("account.page", "warning.connection");
            return wrapper;
        }

        if (wrapper.getMethod().equals("GET")) {
            wrapper.setPage("jsp/account/show.jsp");
            wrapper.setTitle("title.account.show");
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
