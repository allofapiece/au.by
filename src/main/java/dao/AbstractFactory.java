package dao;

import dao.exception.IllegalDAOTypeException;

/**
 * Abstract Factory class, that provides one of the
 * implementations of Data Access Object pattern with
 * {@code create} method.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class AbstractFactory implements Factory {
    /**
     * @param type type of requested DAO implementation
     * @return Factory specific implementation of DAO Factory
     * @throws IllegalDAOTypeException if type of requested DAO does not exist
     */
    @Override
    public Factory create(String type) throws IllegalDAOTypeException {
        switch (type) {
            case "DataBaseDAO":
                return new DataBaseDAOFactory();

            default:
                throw new IllegalDAOTypeException();
        }
    }
}
