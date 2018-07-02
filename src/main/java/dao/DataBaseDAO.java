package dao;

import dao.exception.EntityNotFoundException;

import java.util.List;

/**
 * Class provides access to entities through database.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public interface DataBaseDAO<T> {
    /**
     * @param id id of requested entity
     * @return T found entity
     * @throws EntityNotFoundException if entity did not found
     */
    T find(long id) throws EntityNotFoundException;

    /**
     * @return List the list of found entities
     */
    List<T> findAll();

    /**
     * @param entity object of entity, that need to save in database
     */
    void create(T entity);

    /**
     * @param entity object of entity, that need to delete from database
     * @throws EntityNotFoundException if entity did not found
     */
    void delete(T entity) throws EntityNotFoundException;

    /**
     * @param entity object of entity, that need to update in database. New
     *               object will be inserted in database instead of old
     * @throws EntityNotFoundException if entity did not found.
     */
    void update(T entity) throws EntityNotFoundException;
}
