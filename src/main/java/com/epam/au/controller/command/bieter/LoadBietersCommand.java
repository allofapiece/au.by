package com.epam.au.controller.command.bieter;

import com.epam.au.controller.command.Command;
import com.epam.au.service.loader.BetLoader;
import com.epam.au.service.loader.BieterLoader;
import com.epam.au.service.loader.Loader;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;

public class LoadBietersCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LoadBietersCommand.class);
    private Loader loader;

    public LoadBietersCommand() {
        loader = new BieterLoader();
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        wrapper.setAjax(true);

        if (wrapper.getMethod().equals("POST")) {
            if (wrapper.getRequestParameter("scope").equals("lot")) {
                wrapper.addRequestAttribute(
                        "bieters",
                        ((BieterLoader) loader).loadByLotId(wrapper.getLong("field.id"), wrapper));
            }
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
