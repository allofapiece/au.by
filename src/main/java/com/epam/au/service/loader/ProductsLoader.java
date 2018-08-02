package com.epam.au.service.loader;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.impl.ProductDataBaseDAO;
import com.epam.au.entity.Product;
import com.epam.au.entity.User;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import java.util.List;

public class ProductsLoader implements Loader {
    private static final Logger LOG = Logger.getLogger(ProductsLoader.class);
    private ProductDataBaseDAO dao;

    public ProductsLoader() {
        DataBaseDAOFactory factory;
        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (ProductDataBaseDAO) factory.create("product");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }
    public List<Product> loadAll(HttpWrapper wrapper) {
        return dao.findByUser((User) wrapper.getSessionAttribute("user"));
    }
}
