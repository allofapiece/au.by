package com.epam.au.service.pool;

import com.epam.au.dao.exception.DAOException;

public class ConnectionPoolException extends DAOException {
    private static final long serialVersionUID = 1L;

    public ConnectionPoolException(String message, Exception e){
        super(message);
    }
}

