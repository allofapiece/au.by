package com.epam.au.exception;

import com.epam.au.dao.exception.DAOException;

public class IllegalLotTypeException extends DAOException {
    public IllegalLotTypeException() {
        super("Undefined type of data access object");
    }

    /**
     * @param message
     */
    public IllegalLotTypeException(String message) {
        super(message);
    }
}
