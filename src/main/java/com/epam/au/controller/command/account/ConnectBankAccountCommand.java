package com.epam.au.controller.command.account;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.handler.ConnectBankAccountFormHandler;
import com.epam.au.service.handler.SignInFormHandler;
import com.epam.au.service.wrapper.HttpWrapper;

public class ConnectBankAccountCommand implements Command {
    private ConnectBankAccountFormHandler formHandler;

    public ConnectBankAccountCommand() {
        formHandler = new ConnectBankAccountFormHandler();
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        User user = (User) wrapper.getSessionAttribute("user");

        if (user == null) {
            wrapper.setPage("fc?command=signin");
            wrapper.setIsUpdated(true);
            wrapper.addError("signup.page", "warning.permission");
            return wrapper;
        }

        if (wrapper.getMethod().equals("GET")) {
            wrapper.setPage("jsp/account/connect.jsp");
            wrapper.setTitle("title.account.connect");
        } else {
            if (formHandler.handle(wrapper)) {
                wrapper.setIsUpdated(true);
                wrapper.setPage("jsp/account/account.jsp");
                wrapper.setTitle("title.account.info");
            } else {
                wrapper.setPage("jsp/account/connect.jsp");
                wrapper.setTitle("title.account.connect");
            }
        }

        return wrapper;
    }
}
