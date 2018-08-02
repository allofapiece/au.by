package com.epam.au.controller.command.product;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.handler.AddProductFormHandler;
import com.epam.au.service.loader.ProductsLoader;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletResponse;

public class ShowProductsCommand implements Command {
    private ProductsLoader loader;

    public ShowProductsCommand() {
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
            wrapper.setPage("product.show");
            wrapper.addRequestAttribute("products", loader.loadAll(wrapper));
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
