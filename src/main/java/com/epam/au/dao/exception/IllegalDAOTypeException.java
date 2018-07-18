package com.epam.au.dao.exception;

public class IllegalDAOTypeException extends DAOException {
    public IllegalDAOTypeException() {
        super("Undefined type of data access object");
    }

    /**
     * @param message
     */
    public IllegalDAOTypeException(String message) {
        super(message);
    }
}
