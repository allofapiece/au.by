package com.epam.au.service.search;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.impl.LotDataBaseDAO;
import com.epam.au.dao.impl.ProductDataBaseDAO;
import com.epam.au.entity.Product;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.Lot;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import java.util.List;

public class LotSearch implements Search {
    private static final Logger LOG = Logger.getLogger(LotSearch.class);
    private LotDataBaseDAO dao;

    public LotSearch() {
        DataBaseDAOFactory factory;
        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (LotDataBaseDAO) factory.create("lot");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }
    @Override
    public List<Lot> search(Criteria condition, HttpWrapper wrapper) {
        List<Lot> lots = null;

        try {
            lots = dao.findLotsByNameLike(condition.getValue());
        } catch (DAOException e) {
            LOG.error("DAO error");
        }

        return lots;
    }
}
