package com.epam.au.controller.command;


import com.epam.au.service.wrapper.HttpWrapper;

/**
 * Command class as part of GoF pattern.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public interface Command {

    /**
     * Execute action for command GoF pattern.
     *
     * @param wrapper wrapper for request, response and other.
     * @return HttpWrapper same wrapper.
     */
    HttpWrapper execute(HttpWrapper wrapper);
}
