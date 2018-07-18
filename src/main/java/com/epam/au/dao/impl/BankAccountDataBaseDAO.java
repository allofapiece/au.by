package com.epam.au.dao.impl;

import com.epam.au.bundle.QueryBundle;
import com.epam.au.bundle.QueryBundleFactory;
import com.epam.au.bundle.exception.IllegalQueryBundleException;
import com.epam.au.dao.DataBaseDAO;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.EntityNotFoundException;
import com.epam.au.dao.exception.UnsupportedDAOMethodException;
import com.epam.au.entity.Account;
import com.epam.au.entity.AccountStatus;
import com.epam.au.entity.User;
import com.epam.au.entity.UserStatus;
import org.apache.log4j.Logger;
import com.epam.au.service.pool.ConnectionPool;
import com.epam.au.service.pool.ConnectionPoolException;

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
public class BankAccountDataBaseDAO implements DataBaseDAO {
    private static final Logger LOG = Logger.getLogger(UserDataBaseDAO.class);
    private QueryBundle queryBundle;
    private ConnectionPool connectionPool;

    /**
     * Default constructor.
     */
    public BankAccountDataBaseDAO() {
        connectionPool = ConnectionPool.getInstance();
        try {
            QueryBundleFactory queryBundleFactory = new QueryBundleFactory();
            queryBundle = queryBundleFactory.create("account");
        } catch (IllegalQueryBundleException e) {
            LOG.error("Illegal query com.epam.au.bundle", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object find(long id) throws DAOException {
        throw new UnsupportedDAOMethodException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List findAll() throws DAOException  {
        throw new UnsupportedDAOMethodException();
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
    public void delete(Object entity) throws DAOException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Object entity) throws DAOException  {

    }

    public Account findByNumber(String number) {
        Account account = new Account();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.one.by_number"));
            stmt.setString(1, number);
            rs = stmt.executeQuery();

            account.setNumber(number);
            while (rs.next()) {
                account.setStatus(AccountStatus.valueOf(rs.getString("status").toUpperCase()));
                account.setMoney(rs.getDouble("money"));
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return account;
    }
}
