package com.epam.au.dao.impl;

import com.epam.au.bundle.QueryBundle;
import com.epam.au.bundle.QueryBundleFactory;
import com.epam.au.bundle.exception.IllegalQueryBundleException;
import com.epam.au.dao.DataBaseDAO;
import com.epam.au.dao.exception.EntityNotFoundException;
import com.epam.au.entity.Product;
import com.epam.au.service.pool.ConnectionPool;
import com.epam.au.service.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Specific impl of file com.epam.au.dao for sweet com.epam.au.entity
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class ProductDataBaseDAO implements DataBaseDAO {
    private static final Logger LOG = Logger.getLogger(UserDataBaseDAO.class);
    private QueryBundle queryBundle;
    private ConnectionPool connectionPool;

    /**
     * Default constructor.
     */
    public ProductDataBaseDAO() {
        connectionPool = ConnectionPool.getInstance();
        try {
            QueryBundleFactory queryBundleFactory = new QueryBundleFactory();
            queryBundle = queryBundleFactory.create("product");
        } catch (IllegalQueryBundleException e) {
            LOG.error("Illegal query bundle", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object find(long id) throws EntityNotFoundException {
        Product product = new Product();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.one.by_id"));
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            product.setId(id);
            while (rs.next()) {
                product.setUserId(rs.getLong("user_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setAmount(rs.getInt("amount"));
                product.setPrice(rs.getDouble("price"));
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return product;
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
        Product product = (Product) entity;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("insert.one"));
            stmt.setLong(1, product.getUserId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setInt(4, product.getAmount());
            stmt.setDouble(5, product.getPrice());

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
    public void delete(Object entity) throws EntityNotFoundException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Object entity) throws EntityNotFoundException {

    }
}
