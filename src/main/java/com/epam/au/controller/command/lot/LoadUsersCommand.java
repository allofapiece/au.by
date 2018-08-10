package com.epam.au.controller.command.lot;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.service.filter.*;
import com.epam.au.service.loader.Loader;
import com.epam.au.service.loader.LotLoader;
import com.epam.au.service.loader.UserLoader;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoadUsersCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LoadUsersCommand.class);
    private Loader loader;

    public LoadUsersCommand() {
        loader = new UserLoader();
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        wrapper.setAjax(true);

        if (wrapper.getMethod().equals("POST")) {
            String scope = wrapper.getRequestParameter("scope");
            if (scope.equals("one")) {
                wrapper.addRequestAttribute("user", loader.load(wrapper.getLong("field.id")));
            }
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
