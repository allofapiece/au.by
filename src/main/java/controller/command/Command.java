package controller.command;


import controller.ResponseInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    ResponseInfo execute(HttpServletRequest req, HttpServletResponse resp);
}
