package com.epam.au.service.deleter;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.EntityNotFoundException;
import com.epam.au.dao.impl.ProductDataBaseDAO;
import com.epam.au.entity.Product;
import com.epam.au.entity.ProductStatus;
import com.epam.au.entity.User;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

public class ProductDeleter implements Deleter {
    private static final Logger LOG = Logger.getLogger(ProductDeleter.class);
    private ProductDataBaseDAO dao;

    public ProductDeleter() {
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (ProductDataBaseDAO) factory.create("product");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }
    @Override
    public HttpWrapper delete(HttpWrapper wrapper) {
        long id = Long.parseLong(wrapper.getRequestParameter("id"));
        User user = wrapper.getUser();

        try {
            Product product = (Product) dao.find(id);

            if (product.getUserId() != user.getId()) {
                wrapper.addError("product.summary", "error.exist.message");
                return wrapper;
            }

            product.setStatus(ProductStatus.DELETED);

            dao.update(product);
        } catch (EntityNotFoundException e) {
            LOG.error("Request product to delete was not found", e);
        }

        return wrapper;
    }
}
