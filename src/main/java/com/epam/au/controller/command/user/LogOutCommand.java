package com.epam.au.controller.command.user;

import com.epam.au.controller.command.Command;
import com.epam.au.service.wrapper.HttpWrapper;

public class LogOutCommand implements Command {

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        if (wrapper.getSessionAttribute("user") != null) {
            wrapper.removeSessionAttribute("user");
            wrapper.setPage("user.login");
        } else {
            wrapper.setPage("user.login");
        }

        return wrapper;
    }
}
