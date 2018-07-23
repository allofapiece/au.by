package com.epam.au.controller.command.product;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.deleter.Deleter;
import com.epam.au.service.deleter.ProductDeleter;
import com.epam.au.service.handler.AddProductFormHandler;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletResponse;

public class DeleteProductCommand implements Command {
    private Deleter deleter;

    public DeleteProductCommand() {
        deleter = new ProductDeleter();
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

        if (wrapper.getMethod().equals("POST")) {
            deleter.delete(wrapper);
            wrapper.setPage("product.show");
        } else {
            wrapper.addError("product.summary", "error.exist.message");
        }

        return wrapper;
    }
}
