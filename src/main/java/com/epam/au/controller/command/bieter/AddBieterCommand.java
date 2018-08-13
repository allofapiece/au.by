package com.epam.au.controller.command.bieter;

import com.epam.au.controller.Page;
import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.handler.AddBieterHandler;
import com.epam.au.service.handler.AddLotFormHandler;
import com.epam.au.service.loader.ProductsLoader;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletResponse;

public class AddBieterCommand implements Command {
    private AddBieterHandler handler;
    private ProductsLoader loader;

    public AddBieterCommand() {
        handler = new AddBieterHandler();
        loader = new ProductsLoader();
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

        if (wrapper.getMethod().equals("GET")) {
            if (handler.handle(wrapper)) {
                wrapper.setIsUpdated(true);
                wrapper.setPage(new Page(
                        "title.lot.show.one",
                        "/fc?command=lot-show-one&id=" + wrapper.getLong("field.id")));
            } else {
                wrapper.setPage("lot.show.all");
            }
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
