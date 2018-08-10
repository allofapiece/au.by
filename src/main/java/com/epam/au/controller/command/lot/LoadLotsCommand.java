package com.epam.au.controller.command.lot;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.LotStatus;
import com.epam.au.service.filter.*;
import com.epam.au.service.loader.Loader;
import com.epam.au.service.loader.LotLoader;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LoadLotsCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LoadLotsCommand.class);
    private Loader loader;
    private EntityFilterInterface filter;

    public LoadLotsCommand() {
        loader = new LotLoader();
        filter = new LotFilter();
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        wrapper.setAjax(true);

        if (wrapper.getMethod().equals("GET")) {
            String scope = wrapper.getRequestParameter("scope");
            if (scope.equals("all")) {
                try {
                    wrapper.addRequestAttribute("lots", filter.filter(loader.loadAll(wrapper),
                            CriteriaProvider.getInstance().getCriterias(Rule.ALL_LOT_FILTER)));
                } catch (IllegalRuleException e) {
                    LOG.error("Undefined requested filter rule", e);
                }
            } else if (scope.equals("mine")) {
                User user = (User) wrapper.getSessionAttribute("user");

                if (user == null) {
                    wrapper.setPage("user.login");
                    wrapper.setIsUpdated(true);
                    wrapper.addError("page", "warning.permission");
                    return wrapper;
                }

                wrapper.addRequestAttribute("lots", loader.loadByUser(wrapper));
            }
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
