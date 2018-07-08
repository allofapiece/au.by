package service.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FormHandler {
    boolean handle(HttpServletRequest req, HttpServletResponse resp);
}
