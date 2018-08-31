package com.epam.au.dao.impl;

import com.epam.au.bundle.LocalizationBundle;
import com.epam.au.bundle.QueryBundle;
import com.epam.au.bundle.QueryBundleFactory;
import com.epam.au.bundle.exception.IllegalQueryBundleException;
import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAO;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.EntityNotFoundException;
import com.epam.au.entity.AuctionType;
import com.epam.au.entity.Product;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.*;
import com.epam.au.service.pool.ConnectionPool;
import com.epam.au.service.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
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
    private QueryBundle eventQueryBundle;
    private LocalizationBundle localizationBundle;
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
            eventQueryBundle = queryBundleFactory.create("event");
            localizationBundle = LocalizationBundle.getInstance();
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
        Lot lot = null;
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
                lot = fillLotFromResultSet(rs);
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

        return lot;
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

    public List<Lot> findLotsByNameLike(String name) throws DAOException {
        List<Lot> lots = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.any.by_name.like"));
            stmt.setString(1, "%" + name + "%");
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
        lot.setStartTime(rs.getTimestamp("start_time"));
        lot.setEndTime(rs.getTimestamp("end_time"));
        lot.setMessage(rs.getString("message"));
        lot.setUpdateAt(rs.getTimestamp("update_at"));
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
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            switch (lot.getAuctionType()) {
                case BLITZ:
                    stmt = prepareBlitzLot(lot, conn, queryBundle.getQuery("insert.one.blitz"));
                    break;

                case ENGLISH:
                    stmt = prepareEnglishLot(lot, conn, queryBundle.getQuery("insert.one.english"));
                    break;

                case INTERNET:
                    stmt = prepareInternetLot(lot, conn, queryBundle.getQuery("insert.one.internet"));
                    break;
            }
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();

            if (rs != null && rs.next()) {
                lot.setId(rs.getLong(1));
            }

            if (lot.getStartTime() != null) {
                createEvent(lot, "start");
            }
            if (lot.getEndTime() != null) {
                createEvent(lot, "end");
            }
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }
    }

    private PreparedStatement prepareBlitzLot(Lot lot, Connection conn, String queryString) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(queryString,
                PreparedStatement.RETURN_GENERATED_KEYS);

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
        stmt.setLong(13, (((BlitzLot) lot).getRoundTime()));
        stmt.setDouble(14, ((BlitzLot) lot).getOutgoingAmount());
        stmt.setTimestamp(15, lot.getStartTime());

        return stmt;
    }

    private PreparedStatement prepareEnglishLot(Lot lot, Connection conn, String queryString) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(queryString,
                PreparedStatement.RETURN_GENERATED_KEYS);

        stmt.setString(1, lot.getName());
        stmt.setString(2, lot.getDescription());
        stmt.setString(3, lot.getStatus().toString().toLowerCase());
        stmt.setString(4, lot.getAuctionType().toString().toLowerCase());
        stmt.setLong(5, lot.getSellerId());
        stmt.setLong(6, lot.getProduct().getId());
        stmt.setInt(7, lot.getProductAmount());
        stmt.setDouble(8, lot.getBeginPrice());
        stmt.setDouble(9, ((EnglishLot) lot).getStepPrice());
        stmt.setLong(10, ((EnglishLot) lot).getBetTime());
        stmt.setTimestamp(11, lot.getStartTime());

        return stmt;
    }

    private PreparedStatement prepareInternetLot(Lot lot, Connection conn, String queryString) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(queryString,
                PreparedStatement.RETURN_GENERATED_KEYS);

        stmt.setString(1, lot.getName());
        stmt.setString(2, lot.getDescription());
        stmt.setString(3, lot.getStatus().toString().toLowerCase());
        stmt.setString(4, lot.getAuctionType().toString().toLowerCase());
        stmt.setLong(5, lot.getSellerId());
        stmt.setLong(6, lot.getProduct().getId());
        stmt.setInt(7, lot.getProductAmount());
        stmt.setDouble(8, lot.getBeginPrice());
        stmt.setDouble(9, ((InternetLot) lot).getBlitzPrice());
        stmt.setLong(10, ((InternetLot) lot).getBetTime());
        stmt.setTimestamp(11, lot.getStartTime());

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
    public void update(Object entity) {
        Lot lot = (Lot) entity;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.takeConnection();
            switch (lot.getAuctionType()) {
                case BLITZ:
                    stmt = prepareBlitzLot(lot, conn, queryBundle.getQuery("update.one.blitz"));
                    stmt.setTimestamp(16, lot.getEndTime());
                    if (lot.getMediatorId() != 0) {
                        stmt.setLong(17, lot.getMediatorId());
                    } else {
                        stmt.setNull(17, Types.INTEGER);
                    }
                    stmt.setLong(18, lot.getId());

                    break;

                case ENGLISH:
                    stmt = prepareEnglishLot(lot, conn, queryBundle.getQuery("update.one.english"));
                    stmt.setTimestamp(12, lot.getEndTime());
                    if (lot.getMediatorId() != 0) {
                        stmt.setLong(13, lot.getMediatorId());
                    } else {
                        stmt.setNull(13, Types.INTEGER);
                    }
                    stmt.setLong(14, lot.getId());
                    break;

                case INTERNET:
                    stmt = prepareInternetLot(lot, conn, queryBundle.getQuery("update.one.internet"));
                    stmt.setTimestamp(12, lot.getEndTime());
                    if (lot.getMediatorId() != 0) {
                        stmt.setLong(13, lot.getMediatorId());
                    } else {
                        stmt.setNull(13, Types.INTEGER);
                    }
                    stmt.setLong(14, lot.getId());

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

    public void updateUpdateAt(long id, Timestamp timestamp) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("update.update-at.by-id"));
            stmt.setTimestamp(1, timestamp);
            stmt.setLong(2, id);

            stmt.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt);
        }
    }

    public void createEvent(Lot lot, String type) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.takeConnection();

            switch (type) {
                case "start":
                    stmt = conn.prepareStatement("CREATE EVENT start_lot_" + lot.getId() +
                            " ON SCHEDULE AT ? " +
                            "DO " +
                            "BEGIN " +
                            "SET @amount = (SELECT count(*) FROM bieters WHERE lot_id = ?);" +
                            "UPDATE lots SET lots.status = IF(@amount >= lots.min_people_amount, 'started', 'closed')," +
                            "lots.message = IF(@amount < lots.min_people_amount, ?, NULL)," +
                            "lots.end_time = IF(@amount < lots.min_people_amount, CURRENT_TIMESTAMP, NULL) " +
                            "WHERE id = ?;" +
                            "END");
                    stmt.setTimestamp(1, lot.getStartTime());
                    stmt.setLong(2, lot.getId());
                    stmt.setString(3, localizationBundle.getValue("lot.schedule.close"));
                    stmt.setLong(4, lot.getId());
                    break;

                case "end":
                    stmt = conn.prepareStatement("CREATE EVENT end_lot_" + lot.getId() +
                            " ON SCHEDULE AT ? DO UPDATE lots SET lots.status = 'completed' WHERE id = ?;");

                    stmt.setTimestamp(1, lot.getStartTime());
                    stmt.setLong(2, lot.getId());
                    break;
            }

            stmt.execute();

            switch (type) {
                case "start":
                    stmt = conn.prepareStatement("CREATE EVENT bets_" + lot.getId() +
                            " ON SCHEDULE AT ? + INTERVAL ? SECOND " +
                            "DO " +
                            "BEGIN " +
                            "SET @amount = (SELECT count(*) FROM bets WHERE lot_id = ?);" +
                            "IF @amount = 0 THEN " +
                            "UPDATE lots SET lots.status = 'closed', end_time = CURRENT_TIMESTAMP WHERE id = ?;" +
                            "UPDATE products SET products.status = 'available' WHERE id = (SELECT product_id FROM lots WHERE id = ?);" +
                            "ELSE " +
                            "UPDATE lots SET status = 'completed', end_time = CURRENT_TIMESTAMP WHERE id = ?;" +
                            "SET @winner_id = (SELECT MAX(id) FROM bets WHERE lot_id = ?);" +

                            "SET @mediator = (SELECT mediator_id FROM lots WHERE id = ?);" +
                            "SET @product = (SELECT product_id FROM lots WHERE id = ?);" +
                            "SET @buyer = (SELECT user_id FROM bets WHERE id = @winner_id);" +
                            "SET @seller = (SELECT seller_id FROM lots WHERE id = ?);" +

                            "INSERT INTO deals (lot_id, seller_id, buyer_id, mediator_id, product_id) VALUES (?, @seller, @buyer, @mediator, @product);" +

                            "UPDATE products SET user_id = @buyer, status = 'available' WHERE id = @product;" +

                            "UPDATE accounts SET money = money + (SELECT MAX(price) FROM bets WHERE user_id = @buyer AND lot_id = ?) " +
                            "WHERE accounts.number = (SELECT account_number FROM users WHERE users.id = @seller);" +

                            "UPDATE accounts SET money = money - (SELECT MAX(price) FROM bets WHERE user_id = @buyer AND lot_id = ?) " +
                            "WHERE accounts.number = (SELECT account_number FROM users WHERE users.id = @buyer);" +
                            "END IF;" +
                            "END");
                    
                    if (lot.getAuctionType() == AuctionType.ENGLISH) {
                        stmt.setLong(2, ((EnglishLot) lot).getBetTime());
                    }
                    if (lot.getAuctionType() == AuctionType.INTERNET) {
                        stmt.setLong(2, ((InternetLot) lot).getBetTime());
                    }
                    stmt.setTimestamp(1, lot.getStartTime());
                    stmt.setLong(3, lot.getId());
                    stmt.setLong(4, lot.getId());
                    stmt.setLong(5, lot.getId());
                    stmt.setLong(6, lot.getId());
                    stmt.setLong(7, lot.getId());
                    stmt.setLong(8, lot.getId());
                    stmt.setLong(9, lot.getId());
                    stmt.setLong(10, lot.getId());
                    stmt.setLong(11, lot.getId());
                    stmt.setLong(12, lot.getId());
                    stmt.setLong(13, lot.getId());
                    break;
            }

            stmt.execute();
        } catch (ConnectionPoolException e) {
            LOG.error("Connection pool error", e);
        } catch (SQLException e) {
            LOG.error("SQL error", e);
        } finally {
            connectionPool.closeConnection(conn, stmt);
        }
    }
}
