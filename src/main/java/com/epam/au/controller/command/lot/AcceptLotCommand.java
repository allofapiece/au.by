package com.epam.au.controller.command.lot;

import com.epam.au.controller.Page;
import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.handler.AcceptLotHandler;
import com.epam.au.service.handler.CancelLotHandler;
import com.epam.au.service.wrapper.HttpWrapper;

public class AcceptLotCommand implements Command {
    private AcceptLotHandler handler;

    public AcceptLotCommand() {
        handler = new AcceptLotHandler();
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        User user = (User) wrapper.getSessionAttribute("user");

        if (user == null) {
            wrapper.setPage("user.login");
            wrapper.setIsUpdated(true);
            wrapper.addError("page", "warning.permission");
            return wrapper;
        }

        if (handler.handle(wrapper)) {
            wrapper.setIsUpdated(true);
            wrapper.setPage(new Page(
                    "title.lot.show.all",
                    "/fc?command=lot-show&scope=all"));
        }

        return wrapper;
    }
}
