package com.epam.au.service.filter;

public class IllegalRuleException extends Exception {
    public IllegalRuleException() {
        super("Undefined filter rule");
    }

    /**
     * @param message
     */
    public IllegalRuleException(String message) {
        super(message);
    }
}
