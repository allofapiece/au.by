package com.epam.au.service.loader;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.impl.BieterDataBaseDAO;
import com.epam.au.entity.Bieter;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.Lot;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import java.util.List;

public class BieterLoader implements Loader {
    private static final Logger LOG = Logger.getLogger(BieterLoader.class);
    private BieterDataBaseDAO dao;

    public BieterLoader() {
        DataBaseDAOFactory factory;
        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (BieterDataBaseDAO) factory.create("bieter");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    public List<Bieter> loadByUser(HttpWrapper wrapper) {
        List<Bieter> bieters = null;
        try {
            bieters = dao.findByUser((User) wrapper.getSessionAttribute("user"));
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
        return bieters;
    }

    public List<Bieter> loadByLot(Lot lot, HttpWrapper wrapper) {
        return loadByLotId(lot.getId(), wrapper);
    }

    public List<Bieter> loadByLotId(long id, HttpWrapper wrapper) {
        List<Bieter> bieters = null;
        try {
            bieters = dao.findByLotId(id);
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
        return bieters;
    }

    public List<Bieter> loadAll(HttpWrapper wrapper) {
        List<Bieter> bieters = null;
        try {
            bieters = dao.findAll();
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
        return bieters;
    }

    public Bieter load(long id) {
        Bieter bieter = null;
        try {
            bieter = (Bieter) dao.find(id);
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
        return bieter;
    }
}
