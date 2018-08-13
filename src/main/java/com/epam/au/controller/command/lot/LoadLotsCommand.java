package com.epam.au.controller.command.lot;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.Lot;
import com.epam.au.entity.lot.LotStatus;
import com.epam.au.service.filter.*;
import com.epam.au.service.loader.Loader;
import com.epam.au.service.loader.LotLoader;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
            List<Lot> lots = null;
            String scope = wrapper.getRequestParameter("scope");
            if (scope.equals("all")) {
                try {
                    lots = (ArrayList) filter.filter(loader.loadAll(wrapper),
                            CriteriaProvider.getInstance().getCriterias(Rule.ALL_LOT_FILTER));
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
                lots = (ArrayList) loader.loadByUser(wrapper);
            }

            String filters = null;
            if ((filters = wrapper.getRequestParameter("filter")) != null && !filters.equals("")) {
                String[] terms = filters.split(",");
                Criteria criteria = new Criteria();
                criteria.setMode("=");
                for (String term : terms) {
                    criteria.addValue(LotStatus.valueOf(term.toUpperCase()));
                }
                Map<String, Criteria> criterias = new HashMap<>();
                criterias.put("status", criteria);
                lots = (ArrayList) filter.filter(lots, criterias);
            }

            wrapper.addRequestAttribute("lots", lots);
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
