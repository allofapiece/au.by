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
import com.epam.au.entity.AuctionType;
import com.epam.au.entity.Bet;
import com.epam.au.entity.Product;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.EnglishLot;
import com.epam.au.entity.lot.InternetLot;
import com.epam.au.entity.lot.Lot;
import com.epam.au.service.pool.ConnectionPool;
import com.epam.au.service.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Specific impl of file com.epam.au.dao for sweet com.epam.au.entity
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class BetDataBaseDAO implements DataBaseDAO {
    private static final Logger LOG = Logger.getLogger(UserDataBaseDAO.class);
    private QueryBundle queryBundle;
    private ConnectionPool connectionPool;
    private LotDataBaseDAO lotDAO;

    /**
     * Default constructor.
     */
    public BetDataBaseDAO() {
        connectionPool = ConnectionPool.getInstance();

        try {
            QueryBundleFactory queryBundleFactory = new QueryBundleFactory();
            queryBundle = queryBundleFactory.create("bet");
        } catch (IllegalQueryBundleException e) {
            LOG.error("Illegal query bundle", e);
        }
        DataBaseDAOFactory factory;
        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            lotDAO = (LotDataBaseDAO) factory.create("lot");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object find(long id) throws DAOException {
        Bet bet = new Bet();
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
                bet.setId(id);
                bet.setLotId(rs.getLong("lot_id"));
                bet.setUserId(rs.getLong("user_id"));
                bet.setPrice(rs.getDouble("price"));
                bet.setTime(rs.getTimestamp("time"));

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

        return bet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Bet> findAll() throws DAOException {
        List<Bet> bets = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.all"));
            rs = stmt.executeQuery();

            while (rs.next()) {
                Bet bet = new Bet();
                bet.setId(rs.getLong("id"));
                bet.setLotId(rs.getLong("lot_id"));
                bet.setUserId(rs.getLong("user_id"));
                bet.setPrice(rs.getDouble("price"));
                bet.setTime(rs.getTimestamp("time"));

                bets.add(bet);
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return bets;
    }

    public List<Bet> findByUser(User user) throws DAOException {
        return findByUserId(user.getId());
    }

    public List<Bet> findByUserId(long id) throws DAOException {
        List<Bet> bets = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.any.by_user_id"));
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Bet bet = new Bet();
                bet.setId(rs.getLong("id"));
                bet.setLotId(rs.getLong("lot_id"));
                bet.setUserId(id);
                bet.setPrice(rs.getDouble("price"));
                bet.setTime(rs.getTimestamp("time"));

                bets.add(bet);
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return bets;
    }

    public List<Bet> findByLot(Lot lot) throws DAOException {
        return findByLotId(lot.getId());
    }

    public List<Bet> findByLotId(long id) throws DAOException {
        List<Bet> bets = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.any.by_lot_id"));
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Bet bet = new Bet();
                bet.setId(rs.getLong("id"));
                bet.setLotId(id);
                bet.setUserId(rs.getLong("user_id"));
                bet.setPrice(rs.getDouble("price"));
                bet.setTime(rs.getTimestamp("time"));

                bets.add(bet);
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return bets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Object entity) {
        Bet bet = (Bet) entity;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("insert.one"));

            stmt.setLong(1, bet.getLotId());
            stmt.setLong(2, bet.getUserId());
            stmt.setDouble(3, bet.getPrice());
            stmt.setTimestamp(4, bet.getTime());

            stmt.executeUpdate();

            updateLotEvent(bet.getLotId());
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

    public void updateLotEvent(long lotId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Lot lot = (Lot) lotDAO.find(lotId);
            conn = connectionPool.takeConnection();

            stmt = conn.prepareStatement("ALTER EVENT bets_" + lotId +
                            " ON SCHEDULE AT ? + INTERVAL ? SECOND;");
            if (lot.getAuctionType() == AuctionType.ENGLISH) {
                stmt.setLong(2, ((EnglishLot) lot).getBetTime());
            }
            if (lot.getAuctionType() == AuctionType.INTERNET) {
                stmt.setLong(2, ((InternetLot) lot).getBetTime());
            }
            stmt.setTimestamp(1, new Timestamp(new Date().getTime()));

            stmt.execute();
        } catch (DAOException e) {
            LOG.error("DAO exception", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt);
        }
    }
}
