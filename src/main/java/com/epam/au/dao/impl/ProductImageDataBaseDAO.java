package com.epam.au.dao.impl;

import com.epam.au.bundle.QueryBundle;
import com.epam.au.bundle.QueryBundleFactory;
import com.epam.au.bundle.exception.IllegalQueryBundleException;
import com.epam.au.dao.DataBaseDAO;
import com.epam.au.dao.exception.EntityNotFoundException;
import com.epam.au.entity.Product;
import com.epam.au.entity.ProductImage;
import com.epam.au.entity.User;
import com.epam.au.service.pool.ConnectionPool;
import com.epam.au.service.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Specific impl of file com.epam.au.dao for sweet com.epam.au.entity
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class ProductImageDataBaseDAO implements DataBaseDAO {
    private static final Logger LOG = Logger.getLogger(UserDataBaseDAO.class);
    private QueryBundle queryBundle;
    private ConnectionPool connectionPool;

    /**
     * Default constructor.
     */
    public ProductImageDataBaseDAO() {
        connectionPool = ConnectionPool.getInstance();
        try {
            QueryBundleFactory queryBundleFactory = new QueryBundleFactory();
            queryBundle = queryBundleFactory.create("product-image");
        } catch (IllegalQueryBundleException e) {
            LOG.error("Illegal query bundle", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object find(long id) throws EntityNotFoundException {
        ProductImage image = new ProductImage();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.one.by_id"));
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            image.setId(id);
            while (rs.next()) {
                image.setProductId(rs.getLong("product_id"));
                image.setUrl(rs.getString("name"));
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return image;
    }

    public List<ProductImage> findByProductId(long id) {
        List<ProductImage> images = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.any.by_product_id"));
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProductImage image = new ProductImage();
                image.setProductId(id);
                image.setId(rs.getLong("id"));
                image.setUrl(rs.getString("url"));
                images.add(image);
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return images;
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
        ProductImage image = (ProductImage) entity;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("insert.one"));
            stmt.setLong(1, image.getProductId());
            stmt.setString(2, image.getUrl());
            stmt.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt);
        }
    }

    public void createAll(List<ProductImage> images) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("insert.one"));

            for (ProductImage image : images) {
                stmt.setLong(1, image.getProductId());
                stmt.setString(2, image.getUrl());
                stmt.addBatch();
            }

            stmt.executeBatch();
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
