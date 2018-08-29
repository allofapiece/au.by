package com.epam.au.controller.command.bet;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.Role;
import com.epam.au.entity.User;
import com.epam.au.service.handler.AddBetHandler;
import com.epam.au.service.handler.AddLotFormHandler;
import com.epam.au.service.loader.ProductsLoader;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletResponse;

public class AddBetCommand implements Command {
    private AddBetHandler formHandler;

    public AddBetCommand() {
        formHandler = new AddBetHandler();
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

        if (!user.hasRole(Role.SOLVENT)) {
            wrapper.setPage("account.connect");
            wrapper.setIsUpdated(true);
            wrapper.addError("page", "warning.permission");
            return wrapper;
        }

        wrapper.setAjax(true);

        if (wrapper.getMethod().equals("POST")) {
            if (formHandler.handle(wrapper)) {
                wrapper.setPage("lot.show.all");
            } else {
                wrapper.setPage("lot.add");
            }
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
