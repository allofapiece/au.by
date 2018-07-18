package com.epam.au.dao;

import com.epam.au.dao.impl.BankAccountDataBaseDAO;
import com.epam.au.dao.impl.ProductDataBaseDAO;
import com.epam.au.dao.impl.UserDataBaseDAO;
import com.epam.au.dao.exception.IllegalDataBaseDAOException;

/**
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class DataBaseDAOFactory implements Factory {
    /**
     * @param type requested com.epam.au.entity type
     * @return DataBaseDAO specific database DAO implementation
     * @throws IllegalDataBaseDAOException if requested type does not exist
     */
    public DataBaseDAO create(String type) throws IllegalDataBaseDAOException {
        switch (type) {
            case "user":
                return new UserDataBaseDAO();

            case "product":
                return new ProductDataBaseDAO();

            case "account":
                return new BankAccountDataBaseDAO();

            default:
                throw new IllegalDataBaseDAOException();
        }
    }
}
