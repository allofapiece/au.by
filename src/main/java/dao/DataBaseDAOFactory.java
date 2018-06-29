package dao;

import dao.impl.ProductDataBaseDAO;
import dao.impl.UserDataBaseDAO;
import dao.exception.IllegalDataBaseDAOException;

/**
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class DataBaseDAOFactory implements Factory {
    /**
     * @param type requested entity type
     * @return DataBaseDAO specific database DAO implementation
     * @throws IllegalDataBaseDAOException if requested type does not exist
     */
    public DataBaseDAO create(String type) throws IllegalDataBaseDAOException {
        switch (type) {
            case "user":
                return new UserDataBaseDAO();

            case "product":
                return new ProductDataBaseDAO();

            default:
                throw new IllegalDataBaseDAOException();
        }
    }
}
