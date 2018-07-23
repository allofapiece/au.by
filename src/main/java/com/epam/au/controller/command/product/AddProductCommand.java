package com.epam.au.controller.command.product;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.handler.AddProductFormHandler;
import com.epam.au.service.handler.SignInFormHandler;
import com.epam.au.service.wrapper.HttpWrapper;

public class AddProductCommand implements Command {
    private AddProductFormHandler formHandler;

    public AddProductCommand() {
        formHandler = new AddProductFormHandler();
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
            wrapper.setPage("product.add");
        } else {
            if (formHandler.handle(wrapper)) {
                wrapper.setIsUpdated(true);
                wrapper.setPage("product.show");
                wrapper.getPage().setPath("/fc?command=product-show");
            } else {
                wrapper.setPage("product.add");
            }
        }

        return wrapper;
    }
}
