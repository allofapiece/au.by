package controller.command.user;

import controller.ResponseInfo;
import controller.command.Command;
import entity.User;
import service.handler.SignupFormHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignUpUserCommand implements Command {
    private SignupFormHandler formHandler;

    public SignUpUserCommand() {
        formHandler = new SignupFormHandler();
    }

    @Override
    public ResponseInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        ResponseInfo responseInfo = new ResponseInfo();
        User user = new User();

        if (null != session.getAttribute("user")) {
            responseInfo.setPage("jsp/main.jsp");
            return responseInfo;
        }

        if (req.getMethod().equals("GET")) {
            responseInfo.setPage("jsp/user/signup.jsp");
        } else {
            if (formHandler.handle(req, resp)) {
                session.setAttribute("user", user);
                responseInfo.setIsUpdated(true);
                responseInfo.setPage("jsp/main.jsp");
            } else {
                req.setAttribute("errors", formHandler.getErrors().getAllErrors());
                responseInfo.setPage("jsp/user/signup.jsp");
            }
        }

        return responseInfo;
    }
}
