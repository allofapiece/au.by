package com.epam.au.controller.command.account;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.loader.UserLoader;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletResponse;

public class ShowBankAccountCommand implements Command {
    private UserLoader loader;

    public ShowBankAccountCommand() {
        loader = new UserLoader();
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        User user = wrapper.getUser();

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

            return wrapper;
        }

        if (wrapper.getMethod().equals("GET")) {
            User targetUser = loader.load(user.getId());

            wrapper.setUser(targetUser);

            wrapper.setPage("account.show");
            wrapper.setTitle("title.account.show");
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
