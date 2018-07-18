package com.epam.au.service.handler;

import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FormHandler {
    boolean handle(HttpWrapper wrapper);
}
