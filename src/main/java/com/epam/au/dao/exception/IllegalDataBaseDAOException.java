package com.epam.au.dao.exception;

public class IllegalDataBaseDAOException extends DAOException {
    public IllegalDataBaseDAOException() {
        super("Undefined database data access object");
    }

    /**
     * @param message
     */
    public IllegalDataBaseDAOException(String message) {
        super(message);
    }
}
