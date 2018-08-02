package com.epam.au.controller.command.lot;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.loader.Loader;
import com.epam.au.service.loader.LotLoader;
import com.epam.au.service.loader.ProductsLoader;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletResponse;

public class ShowLotsCommand implements Command {
    private Loader loader;

    public ShowLotsCommand() {
        loader = new LotLoader();
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

        wrapper.setAjax(true);

        if (wrapper.getMethod().equals("GET")) {
            wrapper.setPage("lot.show.mine");
            wrapper.addRequestAttribute("lots", loader.loadAll(wrapper));
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
