package com.epam.au.service.loader;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.impl.LotDataBaseDAO;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.Lot;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import java.util.List;

public class LotLoader implements Loader {
    private static final Logger LOG = Logger.getLogger(LotLoader.class);
    private LotDataBaseDAO dao;

    public LotLoader() {
        DataBaseDAOFactory factory;
        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (LotDataBaseDAO) factory.create("lot");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    public List<Lot> loadAll(HttpWrapper wrapper) {
        List<Lot> lots = null;
        try {
            lots = dao.findByUser((User) wrapper.getSessionAttribute("user"));
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
        return lots;
    }
}
