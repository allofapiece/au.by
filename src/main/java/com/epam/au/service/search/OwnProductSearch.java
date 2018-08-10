package com.epam.au.service.search;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.impl.ProductDataBaseDAO;
import com.epam.au.entity.Product;
import com.epam.au.entity.User;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import java.util.List;

public class OwnProductSearch implements Search {
    private static final Logger LOG = Logger.getLogger(OwnProductSearch.class);
    private ProductDataBaseDAO dao;

    public OwnProductSearch() {
        DataBaseDAOFactory factory;
        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (ProductDataBaseDAO) factory.create("product");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }
    @Override
    public List<Product> search(Criteria condition, HttpWrapper wrapper) {
        return dao.findStringLikeByUserIdAndName(
                condition.getColumn(),
                condition.getValue(),
                ((User) wrapper.getSessionAttribute("user")).getId()
        );
    }
}
