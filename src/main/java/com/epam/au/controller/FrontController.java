package com.epam.au.controller;

import com.epam.au.controller.command.Command;
import com.epam.au.controller.command.CommandProvider;
import com.epam.au.controller.command.IllegalCommandException;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.impl.BetDataBaseDAO;
import com.epam.au.dao.impl.UserDataBaseDAO;
import org.apache.log4j.Logger;
import com.epam.au.service.wrapper.HttpWrapper;
//import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
public class FrontController extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandProvider commandProvider = new CommandProvider();
        String requestedCommand = req.getParameter("command");

        //TODO delete this CODE
        ////////////////
        UserDataBaseDAO dao = new UserDataBaseDAO();
        try {
            req.getSession().setAttribute("user", dao.find(14));
        } catch (DAOException e) {
            e.printStackTrace();
        }
        ////////////////
        req.removeAttribute("errors");
        req.getSession().removeAttribute("errors");
        HttpWrapper wrapper = new HttpWrapper(req, resp);

        if (requestedCommand == null) {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            try {
                Command command = commandProvider.getCommand(requestedCommand);
                command.execute(wrapper);
            } catch (IllegalCommandException e) {
                LOG.error("Requested command is not defined", e);
            }
        }

        wrapper.go();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
