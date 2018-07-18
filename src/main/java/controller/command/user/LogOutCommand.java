package controller.command.user;

import controller.ResponseInfo;
import controller.command.Command;
import service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        if (wrapper.getSessionAttribute("user") != null) {
            wrapper.removeSessionAttribute("user");
            wrapper.setPage("fc?command=signin");
        } else {
            wrapper.setPage("fc?command=signin");
        }

        return wrapper;
    }
}
