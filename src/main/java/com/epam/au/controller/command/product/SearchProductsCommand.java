package com.epam.au.controller.command.product;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.loader.ProductsLoader;
import com.epam.au.service.search.Criteria;
import com.epam.au.service.search.OwnProductSearch;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletResponse;

public class SearchProductsCommand implements Command {
    private OwnProductSearch search;

    public SearchProductsCommand() {
        search = new OwnProductSearch();
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
            Criteria criteria = new Criteria("name", wrapper.getRequestParameter("searchValue"));
            wrapper.addRequestAttribute("products", search.search(criteria, wrapper));
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
