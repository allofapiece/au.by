package com.epam.au.dao.impl;

import com.epam.au.bundle.QueryBundle;
import com.epam.au.bundle.QueryBundleFactory;
import com.epam.au.bundle.exception.IllegalQueryBundleException;
import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAO;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.EntityNotFoundException;
import com.epam.au.dao.exception.IllegalDAOTypeException;
import com.epam.au.entity.AuctionType;
import com.epam.au.entity.Product;
import com.epam.au.entity.ProductStatus;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.*;
import com.epam.au.service.pool.ConnectionPool;
import com.epam.au.service.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import javax.sql.PooledConnection;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Specific impl of file com.epam.au.dao for sweet com.epam.au.entity
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class LotDataBaseDAO implements DataBaseDAO {
    private static final Logger LOG = Logger.getLogger(UserDataBaseDAO.class);
    private QueryBundle queryBundle;
    private ConnectionPool connectionPool;
    private ProductDataBaseDAO productDAO;

    /**
     * Default constructor.
     */
    public LotDataBaseDAO() {
        connectionPool = ConnectionPool.getInstance();

        try {
            QueryBundleFactory queryBundleFactory = new QueryBundleFactory();
            queryBundle = queryBundleFactory.create("lot");
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
    public Object find(long id) throws EntityNotFoundException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Lot> findAll() throws DAOException {
        List<Lot> lots = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.all"));
            rs = stmt.executeQuery();

            while (rs.next()) {
                Lot lot = fillLotFromResultSet(rs);
                lots.add(lot);
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return lots;
    }

    public List<Lot> findByUser(User user) throws DAOException {
        return findByUserId(user.getId());
    }

    public List<Lot> findByUserId(long id) throws DAOException {
        List<Lot> lots = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.any.by_user_id"));
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Lot lot = fillLotFromResultSet(rs);
                lot.setSellerId(id);
                lots.add(lot);
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return lots;
    }

    private Lot fillLotFromResultSet(ResultSet rs) throws SQLException, DAOException {
        Lot lot = null;
        switch (rs.getString("auction_type")) {
            case "blitz":
                lot = new BlitzLot();
                generalFilling(lot, rs);
                ((BlitzLot) lot).setOutgoingAmount(rs.getDouble("outgoing_amount"));
                ((BlitzLot) lot).setRoundAmount(rs.getInt("round_amount"));
                ((BlitzLot) lot).setRoundTime(rs.getLong("round_time"));
                ((BlitzLot) lot).setMinPeopleAmount(rs.getInt("min_people_amount"));
                ((BlitzLot) lot).setMaxPeopleAmount(rs.getInt("max_people_amount"));
                ((BlitzLot) lot).setBlitzPrice(rs.getDouble("blitz_price"));
                break;

            case "english":
                lot = new EnglishLot();
                generalFilling(lot, rs);
                ((EnglishLot) lot).setBetTime(rs.getLong("bet_time"));
                ((EnglishLot) lot).setStepPrice(rs.getDouble("step_price"));
                break;

            case "internet":
                lot = new InternetLot();
                generalFilling(lot, rs);
                ((InternetLot) lot).setBetTime(rs.getLong("bet_time"));
                ((InternetLot) lot).setBlitzPrice(rs.getDouble("blitz_price"));
                break;
        }

        return lot;
    }

    private Lot generalFilling(Lot lot, ResultSet rs) throws SQLException, DAOException {
        //TODO setting data time for start and end lot
        lot.setId(rs.getLong("id"));
        lot.setSellerId(rs.getLong("seller_id"));
        lot.setName(rs.getString("name"));
        lot.setDescription(rs.getString("description"));
        lot.setStatus(LotStatus.valueOf(rs.getString("status").toUpperCase()));
        lot.setAuctionType(AuctionType.valueOf(rs.getString("auction_type").toUpperCase()));
        lot.setMediatorId(rs.getLong("mediator_id"));
        lot.setProduct((Product) productDAO.find(rs.getLong("product_id")));
        lot.setProductAmount(rs.getInt("product_amount"));
        lot.setBeginPrice(rs.getDouble("begin_price"));

        return lot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Object entity) {
        Lot lot = (Lot) entity;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.takeConnection();
            switch (lot.getAuctionType()) {
                case BLITZ:
                    stmt = prepareBlitzLot(lot, conn);
                    break;

                case ENGLISH:
                    stmt = prepareEnglishLot(lot, conn);
                    break;

                case INTERNET:
                    stmt = prepareInternetLot(lot, conn);
                    break;
            }
            stmt.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt);
        }
    }

    private PreparedStatement prepareBlitzLot(Lot lot, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(queryBundle.getQuery("insert.one.blitz"));

        stmt.setString(1, lot.getName());
        stmt.setString(2, lot.getDescription());
        stmt.setString(3, lot.getStatus().toString().toLowerCase());
        stmt.setString(4, lot.getAuctionType().toString().toLowerCase());
        stmt.setLong(5, lot.getSellerId());
        stmt.setLong(6, lot.getProduct().getId());
        stmt.setInt(7, lot.getProductAmount());
        stmt.setDouble(8, lot.getBeginPrice());
        stmt.setDouble(9, ((BlitzLot) lot).getBlitzPrice());
        stmt.setInt(10, ((BlitzLot) lot).getMinPeopleAmount());
        stmt.setInt(11, ((BlitzLot) lot).getMaxPeopleAmount());
        stmt.setInt(12, ((BlitzLot) lot).getRoundAmount());
        stmt.setTime(13, (new Time(((BlitzLot) lot).getRoundTime())));
        stmt.setDouble(14, ((BlitzLot) lot).getOutgoingAmount());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = format.format(lot.getStartTime());
        stmt.setString(15, datetime);

        return stmt;
    }

    private PreparedStatement prepareEnglishLot(Lot lot, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(queryBundle.getQuery("insert.one.english"));

        stmt.setString(1, lot.getName());
        stmt.setString(2, lot.getDescription());
        stmt.setString(3, lot.getStatus().toString().toLowerCase());
        stmt.setString(4, lot.getAuctionType().toString().toLowerCase());
        stmt.setLong(5, lot.getSellerId());
        stmt.setLong(6, lot.getProduct().getId());
        stmt.setInt(7, lot.getProductAmount());
        stmt.setDouble(8, lot.getBeginPrice());
        stmt.setDouble(9, ((EnglishLot) lot).getStepPrice());
        stmt.setTime(10, new Time(((EnglishLot) lot).getBetTime()));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = format.format(lot.getStartTime());
        stmt.setString(11, datetime);

        return stmt;
    }

    private PreparedStatement prepareInternetLot(Lot lot, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(queryBundle.getQuery("insert.one.internet"));

        stmt.setString(1, lot.getName());
        stmt.setString(2, lot.getDescription());
        stmt.setString(3, lot.getStatus().toString().toLowerCase());
        stmt.setString(4, lot.getAuctionType().toString().toLowerCase());
        stmt.setLong(5, lot.getSellerId());
        stmt.setLong(6, lot.getProduct().getId());
        stmt.setInt(7, lot.getProductAmount());
        stmt.setDouble(8, lot.getBeginPrice());
        stmt.setDouble(9, ((InternetLot) lot).getBlitzPrice());
        stmt.setTime(10, new Time(((InternetLot) lot).getBetTime()));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = format.format(lot.getStartTime());
        stmt.setString(11, datetime);

        return stmt;
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
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Object entity) throws EntityNotFoundException {

    }
}
