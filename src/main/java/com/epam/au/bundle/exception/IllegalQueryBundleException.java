package com.epam.au.bundle.exception;

public class IllegalQueryBundleException extends Exception {
    /**
     * Default constructor.
     */
    public IllegalQueryBundleException() {
        super("Undefined query com.epam.au.bundle");
    }

    /**
     * @param message
     */
    public IllegalQueryBundleException(String message) {
        super(message);
    }
}
