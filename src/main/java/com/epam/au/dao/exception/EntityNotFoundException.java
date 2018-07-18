package com.epam.au.dao.exception;

/**
 * This com.epam.au.exception throws when providers do not find com.epam.au.entity
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class EntityNotFoundException extends DAOException {

    public EntityNotFoundException() {
    }

    /**
     * @param message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
