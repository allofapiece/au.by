package com.epam.au.controller.command.lot;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.Role;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.Lot;
import com.epam.au.service.filter.*;
import com.epam.au.service.search.Criteria;
import com.epam.au.service.search.LotSearch;
import com.epam.au.service.search.Search;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class SearchLotsCommand implements Command {
    private static final Logger LOG = Logger.getLogger(SearchLotsCommand.class);
    private Search search;
    private EntityFilterInterface filter;

    public SearchLotsCommand() {
        search = new LotSearch();
        filter = new LotFilter();
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

            List<Lot> lots = (List<Lot>) search.search(criteria, wrapper);

            if (!wrapper.getUser().hasRole(Role.ADMIN)) {
                try {
                    lots = (ArrayList) filter.filter(lots,
                            CriteriaProvider.getInstance().getCriterias(Rule.ALL_LOT_FILTER));
                } catch (IllegalRuleException e) {
                    LOG.error("Undefined requested filter rule", e);
                }
            }

            wrapper.addRequestAttribute("lots", lots);
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
