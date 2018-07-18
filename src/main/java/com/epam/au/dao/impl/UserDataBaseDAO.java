package com.epam.au.dao.impl;

import com.epam.au.bundle.exception.IllegalQueryBundleException;
import com.epam.au.bundle.QueryBundle;
import com.epam.au.bundle.QueryBundleFactory;
import com.epam.au.dao.DataBaseDAO;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.EntityNotFoundException;
import com.epam.au.dao.exception.UnsupportedDAOMethodException;
import com.epam.au.entity.*;
import org.apache.log4j.Logger;
import com.epam.au.service.pool.ConnectionPool;
import com.epam.au.service.pool.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Specific impl of file com.epam.au.dao for gift com.epam.au.entity.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class UserDataBaseDAO implements DataBaseDAO {
    private static final Logger LOG = Logger.getLogger(UserDataBaseDAO.class);
    private QueryBundle queryBundle;
    private ConnectionPool connectionPool;

    /**
     * Default constructor.
     */
    public UserDataBaseDAO() {
        connectionPool = ConnectionPool.getInstance();
        try {
            QueryBundleFactory queryBundleFactory = new QueryBundleFactory();
            queryBundle = queryBundleFactory.create("user");
        } catch (IllegalQueryBundleException e) {
            LOG.error("Illegal query com.epam.au.bundle", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User find(long id) throws DAOException {
        User user = new User();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int count = 0;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(queryBundle.getQuery("select.any.by_id.all_info"));
            statement.setDouble(1, id);
            rs = statement.executeQuery();

            while (rs.next()) {
                user.setId(id);
                user.setEmail(rs.getString("email"));
                user.setStatus(UserStatus.valueOf(rs.getString("users.status").toUpperCase()));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setPassword(rs.getString("password"));
                user.setSalt(rs.getString("salt"));
                String accountNumber = rs.getString("account.number");

                if (accountNumber != null) {
                    Account account = new Account(
                            accountNumber,
                            AccountStatus.valueOf(rs.getString("account.status").toUpperCase()),
                            rs.getDouble("money")
                    );
                    user.setAccount(account);
                }

                count++;
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        if (count != 1) {
            throw new EntityNotFoundException();
        }

        user.setRoles(findRolesByUserId(id));

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List findAll() throws DAOException {
        throw new UnsupportedDAOMethodException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Object entity) {
        User user = (User) entity;
        long generatedId = 0;
        Connection conn = null;
         PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(
                    queryBundle.getQuery("insert.one.main_info"),
                    stmt.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getStatus().toString().toLowerCase());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getSurname());

            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                generatedId = rs.getLong(1);
            }

            stmt = conn.prepareStatement(queryBundle.getQuery("insert.one.credentials"));
            stmt.setLong(1, generatedId);
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getSalt());
            stmt.executeUpdate();

            stmt = conn.prepareStatement(queryBundle.getQuery("insert.one.role"));
            for (Role role : user.getRoles()) {
                stmt.setLong(1, generatedId);
                stmt.setString(2, role.toString().toLowerCase());
                stmt.executeUpdate();
            }

            LOG.info("New user add with email = " + user.getEmail());
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
    public void delete(Object entity) throws DAOException {
        throw new UnsupportedDAOMethodException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Object entity) throws DAOException {
        User user = (User) entity;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("update.one"));
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getStatus().toString().toLowerCase());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getSurname());
            stmt.setString(5, user.getAccount().getNumber());
            stmt.setLong(6, user.getId());

            stmt.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt);
        }
    }

    public List<Role> findRolesByUser(User user) {
        return findRolesByUserId(user.getId());
    }

    public List<Role> findRolesByUserId(long id) {
        List<Role> roles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(queryBundle.getQuery("select.any.by_id.roles"));
            statement.setDouble(1, id);

            rs = statement.executeQuery();

            while (rs.next()) {
                roles.add(Role.valueOf(rs.getString("role").toUpperCase()));
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return roles;
    }

    public User findCredentialsByEmail(String email) {
        User user = new User();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.any.by_email.credentials"));
            stmt.setString(1, email);

            rs = stmt.executeQuery();

            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setEmail(email);
                user.setPassword(rs.getString("password"));
                user.setSalt(rs.getString("salt"));
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return user;
    }

    public User findByEmail(String email) {
        User user = new User();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.any.by_email.all_info"));
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setStatus(UserStatus.valueOf(rs.getString("users.status").toUpperCase()));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setPassword(rs.getString("password"));
                user.setSalt(rs.getString("salt"));
                String accountNumber = rs.getString("accounts.number");

                if (accountNumber != null) {
                    Account account = new Account(
                            accountNumber,
                            AccountStatus.valueOf(rs.getString("accounts.status").toUpperCase()),
                            rs.getDouble("money")
                    );
                    user.setAccount(account);
                }
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        user.setRoles(findRolesByUser(user));

        return user;
    }

    public int amountByEmail(String email) {
        int amount = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.count.by_email"));
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            while (rs.next()) {
                amount = rs.getInt("amount");
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return amount;
    }
}
