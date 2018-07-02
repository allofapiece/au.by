package dao.impl;

import bundle.exception.IllegalQueryBundleException;
import bundle.QueryBundle;
import bundle.QueryBundleFactory;
import dao.DataBaseDAO;
import dao.exception.EntityNotFoundException;
import entity.*;
import org.apache.log4j.Logger;
import service.pool.ConnectionPool;
import service.pool.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Specific impl of file dao for gift entity.
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
            LOG.error("Illegal query bundle", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User find(long id) throws EntityNotFoundException {
        User user = null;
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
}
