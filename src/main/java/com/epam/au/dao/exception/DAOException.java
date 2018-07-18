package com.epam.au.dao.exception;

public class DAOException extends Exception {
    /**
     * Default constructor.
     */
    public DAOException() {
        super("Data Access Object error");
    }

    /**
     * @param message
     */
    public DAOException(String message) {
        super(message);
    }
}
