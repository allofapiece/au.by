package controller;

import controller.command.Command;
import controller.command.CommandProvider;
import controller.command.IllegalCommandException;
//import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    //private static final Logger LOG = Logger.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandProvider commandProvider = new CommandProvider();
        ResponseInfo responseInfo = new ResponseInfo();
        String requestedCommand = req.getParameter("command");

        try {
            Command command = commandProvider.getCommand(requestedCommand);
            responseInfo = command.execute(req, resp);
        } catch (IllegalCommandException e) {
            //LOG.error("Requested command is not defined", e);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(responseInfo.getPage());

        if (responseInfo.isUpdated()) {
            resp.sendRedirect(responseInfo.getPage());
        } else {
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
