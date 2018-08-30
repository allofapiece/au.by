package com.epam.au.controller.command;

/**
 * Exception describes undefined name of requested command in command provider.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class IllegalCommandException extends Exception {
    /**
     * Default constructor.
     */
    public IllegalCommandException() {
        super("Undefined command");
    }

    /**
     * @param message describes reason of exception.
     */
    public IllegalCommandException(String message) {
        super(message);
    }
}
