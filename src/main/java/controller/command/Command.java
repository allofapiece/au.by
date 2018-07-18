package controller.command;


import controller.ResponseInfo;
import service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    HttpWrapper execute(HttpWrapper wrapper);
}
