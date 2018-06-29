package dao.impl;

import dao.DataBaseDAO;
import dao.exception.EntityNotFoundException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Specific impl of file dao for gift entity.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class UserDataBaseDAO implements DataBaseDAO {
    private static final Logger LOG = Logger.getLogger(UserDataBaseDAO.class);

    /**
     * Default constructor.
     */
    public UserDataBaseDAO() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object find(int id) throws EntityNotFoundException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List findAll() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Object entity) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Object entity) throws EntityNotFoundException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Object entity) throws EntityNotFoundException {

    }
}
