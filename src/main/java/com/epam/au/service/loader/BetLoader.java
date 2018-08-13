package com.epam.au.service.loader;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.impl.BetDataBaseDAO;
import com.epam.au.dao.impl.LotDataBaseDAO;
import com.epam.au.entity.Bet;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.Lot;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import java.util.List;

public class BetLoader implements Loader {
    private static final Logger LOG = Logger.getLogger(BetLoader.class);
    private BetDataBaseDAO dao;

    public BetLoader() {
        DataBaseDAOFactory factory;
        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (BetDataBaseDAO) factory.create("bet");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    public List<Bet> loadByUser(HttpWrapper wrapper) {
        List<Bet> bets = null;
        try {
            bets = dao.findByUser((User) wrapper.getSessionAttribute("user"));
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
        return bets;
    }

    public List<Bet> loadByLot(Lot lot, HttpWrapper wrapper) {
        return loadByLotId(lot.getId(), wrapper);
    }

    public List<Bet> loadByLotId(long id, HttpWrapper wrapper) {
        List<Bet> bets = null;
        try {
            bets = dao.findByLotId(id);
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
        return bets;
    }

    public List<Bet> loadAll(HttpWrapper wrapper) {
        List<Bet> bets = null;
        try {
            bets = dao.findAll();
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
        return bets;
    }

    public Bet load(long id) {
        Bet bet = null;
        try {
            bet = (Bet) dao.find(id);
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
        return bet;
    }
}
