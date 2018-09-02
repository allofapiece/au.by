package com.epam.au.controller.command.product;

import com.epam.au.controller.Page;
import com.epam.au.controller.command.Command;
import com.epam.au.controller.command.CommandProvider;
import com.epam.au.entity.User;
import com.epam.au.service.handler.AddProductFormHandler;
import com.epam.au.service.handler.SignInFormHandler;
import com.epam.au.service.loader.ProductsLoader;
import com.epam.au.service.wrapper.HttpWrapper;

public class AddProductCommand implements Command {
    private AddProductFormHandler formHandler;
    private ProductsLoader loader;

    public AddProductCommand() {
        formHandler = new AddProductFormHandler();
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
            wrapper.setPage("product.add");
        } else {
            if (formHandler.handle(wrapper)) {
                wrapper.setIsUpdated(true);
                wrapper.setPage(new Page(
                        "title.product.show",
                        "/fc?command=product-show"));
                wrapper.addRequestAttribute("products", loader.loadAll(wrapper));
            } else {
                wrapper.setPage("product.add");
            }
        }

        return wrapper;
    }
}
