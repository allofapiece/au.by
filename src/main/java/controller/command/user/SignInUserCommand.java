package controller.command.user;

import controller.ResponseInfo;
import controller.command.Command;
import entity.User;
import service.handler.SignInFormHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignInUserCommand implements Command {
    private SignInFormHandler formHandler;

    public SignInUserCommand() {
        formHandler = new SignInFormHandler();
    }

    @Override
    public ResponseInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        ResponseInfo responseInfo = new ResponseInfo();
        HttpSession session = req.getSession();

        if (session.getAttribute("user") != null) {
            responseInfo.setPage("jsp/main.jsp");
            return responseInfo;
        }

        User user = new User();

        if (req.getMethod().equals("GET")) {
            responseInfo.setPage("jsp/user/signin.jsp");
        } else {
            if (formHandler.handle(req, resp)) {
                session.setAttribute("user", user);
                //session.setAttribute("isRemembered", req.getParameter("rememberme"));
                responseInfo.setIsUpdated(true);
                responseInfo.setPage("jsp/main.jsp");
            } else {
                req.setAttribute("errors", formHandler.getErrors().getAllErrors());
                responseInfo.setPage("jsp/user/signin.jsp");
            }
        }

        return responseInfo;
    }
}
