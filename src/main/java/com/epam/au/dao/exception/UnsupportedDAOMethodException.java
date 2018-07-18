package com.epam.au.dao.exception;

/**
 * This com.epam.au.exception throws when providers do not find com.epam.au.entity
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class UnsupportedDAOMethodException extends DAOException {

    public UnsupportedDAOMethodException() {
        super("Unsupported method of DAO");
    }

    /**
     * @param message
     */
    public UnsupportedDAOMethodException(String message) {
        super(message);
    }
}
