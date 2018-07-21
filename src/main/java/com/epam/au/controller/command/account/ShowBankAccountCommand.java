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
            wrapper.setPage("user.login");
            wrapper.setTitle("Sign in");
            wrapper.setIsUpdated(true);
            wrapper.addError("page", "warning.permission");
            return wrapper;
        }

        if (user.getAccount() == null) {
            wrapper.setPage("account.connect");
            wrapper.setIsUpdated(true);
            wrapper.addError("account.page", "warning.account.connection");
            return wrapper;
        }

        if (wrapper.getMethod().equals("GET")) {
            wrapper.setPage("account.show");
            wrapper.setTitle("title.account.show");
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
