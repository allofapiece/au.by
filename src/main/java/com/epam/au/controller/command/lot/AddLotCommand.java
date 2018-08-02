package com.epam.au.controller.command.lot;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.handler.AddLotFormHandler;
import com.epam.au.service.handler.AddProductFormHandler;
import com.epam.au.service.loader.ProductsLoader;
import com.epam.au.service.wrapper.HttpWrapper;

public class AddLotCommand implements Command {
    private AddLotFormHandler formHandler;
    private ProductsLoader loader;

    public AddLotCommand() {
        formHandler = new AddLotFormHandler();
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
            wrapper.setPage("lot.add");
        } else {
            if (formHandler.handle(wrapper)) {
                wrapper.setIsUpdated(true);
                wrapper.setPage("lot.show.mine");
                wrapper.addRequestAttribute("lots", loader.loadAll(wrapper));
            } else {
                wrapper.setPage("lot.add");
            }
        }

        return wrapper;
    }
}
