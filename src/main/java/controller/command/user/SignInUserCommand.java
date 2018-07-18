package controller.command.user;

import controller.ResponseInfo;
import controller.command.Command;
import entity.User;
import service.handler.SignInFormHandler;
import service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignInUserCommand implements Command {
    private SignInFormHandler formHandler;

    public SignInUserCommand() {
        formHandler = new SignInFormHandler();
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        if (wrapper.getSessionAttribute("user") != null) {
            wrapper.setPage("jsp/main.jsp");
            wrapper.setIsUpdated(true);
            return wrapper;
        }

        User user = new User();

        if (wrapper.getMethod().equals("GET")) {
            wrapper.setPage("jsp/user/signin.jsp");
            wrapper.setTitle("title.user.signin");
        } else {
            if (formHandler.handle(wrapper)) {
                //session.setAttribute("isRemembered", req.getParameter("rememberme"));
                wrapper.setIsUpdated(true);
                wrapper.setPage("jsp/main.jsp");
            } else {
                wrapper.addRequestAttribute("errors", formHandler.getErrors().getAllErrors());
                wrapper.setPage("jsp/user/signin.jsp");
                wrapper.setTitle("title.user.signin");
            }
        }

        return wrapper;
    }
}
