package com.epam.au.dao;

import com.epam.au.dao.impl.*;
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

            case "lot":
                return new LotDataBaseDAO();

            case "bet":
                return new BetDataBaseDAO();

            case "product-image":
                return new ProductImageDataBaseDAO();

            default:
                throw new IllegalDataBaseDAOException();
        }
    }
}
