package com.epam.au.dao.impl;

import com.epam.au.bundle.QueryBundle;
import com.epam.au.bundle.QueryBundleFactory;
import com.epam.au.bundle.exception.IllegalQueryBundleException;
import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAO;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.EntityNotFoundException;
import com.epam.au.dao.exception.UnsupportedDAOMethodException;
import com.epam.au.entity.Bieter;
import com.epam.au.entity.Bieter;
import com.epam.au.entity.Product;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.Lot;
import com.epam.au.service.pool.ConnectionPool;
import com.epam.au.service.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Specific impl of file com.epam.au.dao for sweet com.epam.au.entity
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class BieterDataBaseDAO implements DataBaseDAO {
    private static final Logger LOG = Logger.getLogger(UserDataBaseDAO.class);
    private QueryBundle queryBundle;
    private ConnectionPool connectionPool;
    private ProductDataBaseDAO productDAO;

    /**
     * Default constructor.
     */
    public BieterDataBaseDAO() {
        connectionPool = ConnectionPool.getInstance();

        try {
            QueryBundleFactory queryBundleFactory = new QueryBundleFactory();
            queryBundle = queryBundleFactory.create("bieter");
        } catch (IllegalQueryBundleException e) {
            LOG.error("Illegal query bundle", e);
        }
        DataBaseDAOFactory factory;
        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            productDAO = (ProductDataBaseDAO) factory.create("product");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object find(long id) throws DAOException {
        Bieter bieter = new Bieter();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.one.by_id"));
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                bieter.setId(id);
                bieter.setUserId(rs.getLong("user_id"));
                bieter.setLotId(rs.getLong("lot_id"));
                bieter.setJoinTime(rs.getTimestamp("join_time"));

                count++;
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        if (count != 1) {
            throw new EntityNotFoundException();
        }

        return bieter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Bieter> findAll() throws DAOException {
        List<Bieter> bieters = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.all"));
            rs = stmt.executeQuery();

            while (rs.next()) {
                Bieter bieter = new Bieter();
                bieter.setId(rs.getLong("id"));
                bieter.setUserId(rs.getLong("user_id"));
                bieter.setLotId(rs.getLong("lot_id"));
                bieter.setJoinTime(rs.getTimestamp("join_time"));

                bieters.add(bieter);
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return bieters;
    }

    public List<Bieter> findByUser(User user) throws DAOException {
        return findByUserId(user.getId());
    }

    public List<Bieter> findByUserId(long id) throws DAOException {
        List<Bieter> bieters = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.any.by_user_id"));
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Bieter bieter = new Bieter();
                bieter.setId(rs.getLong("id"));
                bieter.setUserId(id);
                bieter.setLotId(rs.getLong("lot_id"));
                bieter.setJoinTime(rs.getTimestamp("join_time"));

                bieters.add(bieter);
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return bieters;
    }

    public List<Bieter> findByLot(Lot lot) throws DAOException {
        return findByLotId(lot.getId());
    }

    public List<Bieter> findByLotId(long id) throws DAOException {
        List<Bieter> bieters = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.any.by_lot_id"));
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Bieter bieter = new Bieter();
                bieter.setId(rs.getLong("id"));
                bieter.setUserId(rs.getLong("user_id"));
                bieter.setLotId(id);
                bieter.setJoinTime(rs.getTimestamp("join_time"));

                bieters.add(bieter);
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return bieters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Object entity) {
        Bieter bieter = (Bieter) entity;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("insert.one"));

            stmt.setLong(1, bieter.getUserId());
            stmt.setLong(2, bieter.getLotId());
            stmt.setTimestamp(3, bieter.getJoinTime());

            stmt.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Object entity) throws DAOException {
        Product product = (Product) entity;
        deleteById(product.getId());
    }

    public void deleteById(long id) throws UnsupportedDAOMethodException {
        throw new UnsupportedDAOMethodException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Object entity) throws DAOException {
        throw new UnsupportedDAOMethodException();
    }
}
