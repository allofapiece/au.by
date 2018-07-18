package service.handler;

import service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FormHandler {
    boolean handle(HttpWrapper wrapper);
}
