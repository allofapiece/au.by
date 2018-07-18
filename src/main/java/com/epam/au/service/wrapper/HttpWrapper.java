package com.epam.au.service.wrapper;

import com.sun.deploy.net.HttpRequest;
import com.epam.au.controller.ResponseInfo;
import com.epam.au.service.validator.Errors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpWrapper {
    private ResponseInfo responseInfo;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private Map<String, Object> reqAttrs;
    private Map<String, String> reqParams;
    private Map<String, Object> sessionAttrs;
    private Errors errors;

    public HttpWrapper() {
        responseInfo = new ResponseInfo();
        reqAttrs = new HashMap<>();
        reqParams = new HashMap<>();
        sessionAttrs = new HashMap<>();
        errors = new Errors();
    }

    public HttpWrapper(HttpServletRequest request) {
        this();
        setRequest(request);
    }

    public HttpWrapper(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) {
        this();
        setRequest(request);
        setResponse(response);
        setSession(session);
    }

    public HttpWrapper(HttpServletRequest request, HttpServletResponse response) {
        this();
        setRequest(request);
        setResponse(response);
        setSession(request.getSession());
    }

    public void go(RequestDispatcher requestDispatcher)
            throws ServletException, IOException {
        eject();

        if (getHttpError() != 0) {
            response.sendError(responseInfo.getHttpError());
        }

        if (responseInfo.isUpdated()) {
            response.sendRedirect(responseInfo.getPage());
        } else {
            requestDispatcher.forward(request, response);
        }
    }

    public void go() throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(responseInfo.getPage());
        go(requestDispatcher);
    }

    public void fill(HttpServletRequest req) {
        fill(req, req.getSession());
    }

    public void fill(
            HttpServletRequest req,
            HttpSession session
    ) {
        this.request = req;
        this.session = session;

        fillRequestAttributes(req);
        fillRequestParameters(req);
        fillSessionAttributes(session);
    }

    public void fillRequestAttributes(HttpServletRequest request) {
        Enumeration names = request.getAttributeNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            reqAttrs.put(name, request.getAttribute(name));
            request.removeAttribute(name);
        }
    }

    public void fillRequestParameters(HttpServletRequest request) {
        Enumeration names = request.getParameterNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            reqParams.put(name, request.getParameter(name));
            request.removeAttribute(name);
        }
    }

    public void fillSessionAttributes(HttpSession session) {
        Enumeration names = session.getAttributeNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            sessionAttrs.put(name, session.getAttribute(name));
            session.removeAttribute(name);
        }
    }

    public void eject() {
        ejectRequestAttributes();
        ejectSessionAttributed();
        ejectResponseInfo();
        ejectErrors();
    }

    public HttpServletRequest ejectRequestAttributes() {
        if (isUpdated()) {
            for (Map.Entry<String, Object> attr : reqAttrs.entrySet()) {
                this.session.setAttribute(attr.getKey(), attr.getValue());
            }
        } else {
            for (Map.Entry<String, Object> attr : reqAttrs.entrySet()) {
                this.request.setAttribute(attr.getKey(), attr.getValue());
            }
        }

        return this.request;
    }

    public HttpSession ejectSessionAttributed() {
        for (Map.Entry<String, Object> attr : sessionAttrs.entrySet()) {
            this.session.setAttribute(attr.getKey(), attr.getValue());
        }

        return this.session;
    }

    public HttpServletRequest ejectResponseInfo() {
        if (isUpdated()) {
            this.session.setAttribute("title", getTitle());
        } else {
            this.request.setAttribute("title", getTitle());
        }

        return this.request;
    }

    public HttpServletRequest ejectErrors() {
        if (getErrors().hasErrors()) {
            if (isUpdated()) {
                this.session.setAttribute("errors", getAllErrors());
            } else {
                this.request.setAttribute("errors", getAllErrors());
            }
        }

        return this.request;
    }

    public String getMethod() {
        return this.request.getMethod();
    }

    public ResponseInfo getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(ResponseInfo responseInfo) {
        this.responseInfo = responseInfo;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        fillRequestParameters(request);
        fillRequestAttributes(request);

        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        fillSessionAttributes(session);

        this.session = session;
    }

    public void addRequestAttribute(String name, Object value) {
        reqAttrs.put(name, value);
    }

    public void removeRequestAttribute(String name) {
        reqAttrs.remove(name);
    }

    public Object getRequestAttribute(String name) {
        return reqAttrs.get(name);
    }

    public void addRequestParameter(String name, String value) {
        reqParams.put(name, value);
    }

    public void removeRequestParameter(String name) {
        reqParams.remove(name);
    }

    public String getRequestParameter(String name) {
        return reqParams.get(name);
    }

    public void addSessionAttribute(String name, Object value) {
        sessionAttrs.put(name, value);
    }

    public void removeSessionAttribute(String name) {
        sessionAttrs.remove(name);
    }

    public Object getSessionAttribute(String name) {
        return sessionAttrs.get(name);
    }

    public boolean hasRequestAttribute(String name) {
        for (Map.Entry<String, String> param : reqParams.entrySet()) {
            if (param.getKey().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public void setPage(String page) {
        responseInfo.setPage(page);
    }

    public String getPage() {
        return responseInfo.getPage();
    }

    public void setIsUpdated(boolean isUpdated) {
        responseInfo.setIsUpdated(isUpdated);
    }

    public boolean isUpdated() {
        return responseInfo.isUpdated();
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Map<String, List<String>> getAllErrors() {
        return errors.getAllErrors();
    }

    public void setAllErrors(Map<String, List<String>> errors) {
        this.errors.setAllErrors(errors);
    }

    public void addErrors(Errors errors) {
        getAllErrors().putAll(errors.getAllErrors());
    }

    public void addErrors(Map<String, List<String>> errors) {
        getAllErrors().putAll(errors);
    }

    public void addError(String field, String message) {
        errors.addFieldError(field, message);
    }

    public String getTitle() {
        return responseInfo.getTitle();
    }

    public void setTitle(String title) {
        responseInfo.setTitle(title);
    }

    public int getHttpError() {
        return responseInfo.getHttpError();
    }

    public void setHttpError(int httpError) {
        responseInfo.setHttpError(httpError);
    }

    public boolean getBoolean(String name) {
        return reqParams.get(name).equals("on");
    }
}
