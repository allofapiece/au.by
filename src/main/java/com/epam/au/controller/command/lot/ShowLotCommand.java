package com.epam.au.controller.command.lot;

import com.epam.au.controller.command.Command;
import com.epam.au.entity.Bieter;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.Lot;
import com.epam.au.service.loader.BieterLoader;
import com.epam.au.service.loader.Loader;
import com.epam.au.service.loader.LotLoader;
import com.epam.au.service.loader.UserLoader;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowLotCommand implements Command {
    private Loader loader;
    private Loader userLoader;
    private Loader bieterLoader;

    public ShowLotCommand() {
        loader = new LotLoader();
        userLoader = new UserLoader();
        bieterLoader = new BieterLoader();
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
            wrapper.setPage("lot.show.one");
            Lot lot = (Lot) loader.load(wrapper.getLong("lot.field.id"));
            List<Bieter> bieters = ((BieterLoader) bieterLoader).loadByLot(lot, wrapper);

            boolean isBieter = false;
            for (Bieter bieter : bieters) {
                if (bieter.getUserId() == wrapper.getUserId()) {
                    isBieter = true;
                    break;
                }
            }

            wrapper.addRequestAttribute("lot", lot);
            wrapper.addRequestAttribute("seller", userLoader.load(lot.getSellerId()));
            wrapper.addRequestAttribute("isBieter", isBieter);
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
