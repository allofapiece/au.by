package com.epam.au.controller.command;


import com.epam.au.controller.ResponseInfo;
import com.epam.au.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    HttpWrapper execute(HttpWrapper wrapper);
}
