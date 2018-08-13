package com.epam.au.controller.command.bet;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.Lot;
import com.epam.au.entity.lot.LotStatus;
import com.epam.au.service.filter.*;
import com.epam.au.service.loader.BetLoader;
import com.epam.au.service.loader.Loader;
import com.epam.au.service.loader.LotLoader;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadBetsCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LoadBetsCommand.class);
    private Loader loader;

    public LoadBetsCommand() {
        loader = new BetLoader();
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        wrapper.setAjax(true);

        if (wrapper.getMethod().equals("POST")) {
            if (wrapper.getRequestParameter("scope").equals("lot")) {
                wrapper.addRequestAttribute(
                        "bets",
                        ((BetLoader) loader).loadByLotId(wrapper.getLong("field.id"), wrapper));
            }
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
