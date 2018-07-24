package com.epam.au.dao.impl;

import com.epam.au.bundle.QueryBundle;
import com.epam.au.bundle.QueryBundleFactory;
import com.epam.au.bundle.exception.IllegalQueryBundleException;
import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAO;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.EntityNotFoundException;
import com.epam.au.entity.Product;
import com.epam.au.entity.ProductImage;
import com.epam.au.entity.ProductStatus;
import com.epam.au.entity.User;
import com.epam.au.service.pool.ConnectionPool;
import com.epam.au.service.pool.ConnectionPoolException;
import com.epam.au.service.wrapper.HttpWrapper;
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
public class ProductDataBaseDAO implements DataBaseDAO {
    private static final Logger LOG = Logger.getLogger(UserDataBaseDAO.class);
    private QueryBundle queryBundle;
    private ConnectionPool connectionPool;
    private ProductImageDataBaseDAO imageDAO;

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
        try {
            DataBaseDAOFactory factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            imageDAO = (ProductImageDataBaseDAO) factory.create("product-image");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
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
                product.setStatus(ProductStatus.valueOf(rs.getString("users.status").toUpperCase()));
            }

            product.setImages(imageDAO.findByProductId(id));
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

    public List<Product> findByUser(User user) {
        return findByUserId(user.getId());
    }

    public List<Product> findByUserId(long id) {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.any.by_user_id"));
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setUserId(id);
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setAmount(rs.getInt("amount"));
                product.setPrice(rs.getDouble("price"));
                product.setStatus(ProductStatus.valueOf(rs.getString("status").toUpperCase()));
                //product.setImages(imageDAO.findByProductId(product.getId()));
                products.add(product);
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return products;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Object entity) {
        Product product = (Product) entity;
        long generatedId = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(
                    queryBundle.getQuery("insert.one"),
                    stmt.RETURN_GENERATED_KEYS
            );
            stmt.setLong(1, product.getUserId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setInt(4, product.getAmount());
            stmt.setDouble(5, product.getPrice());
            stmt.setString(6, product.getStatus().toString().toLowerCase());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();

            /*if (!product.getImages().isEmpty()) {
                if (rs != null && rs.next()) {
                    generatedId = rs.getLong(1);
                }
                for (ProductImage image : product.getImages()) {
                    image.setProductId(generatedId);
                }
                imageDAO.createAll(product.getImages());
            }*/
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Object entity) throws EntityNotFoundException {
        Product product = (Product) entity;
        deleteById(product.getId());
    }

    public void deleteById(long id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("delete.one.by_id"));
            stmt.setLong(1, id);
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
    public void update(Object entity) throws EntityNotFoundException {

    }
}
