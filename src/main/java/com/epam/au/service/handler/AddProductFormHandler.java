package com.epam.au.service.handler;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.impl.BankAccountDataBaseDAO;
import com.epam.au.dao.impl.ProductDataBaseDAO;
import com.epam.au.dao.impl.UserDataBaseDAO;
import com.epam.au.entity.Account;
import com.epam.au.entity.Product;
import com.epam.au.entity.ProductStatus;
import com.epam.au.entity.User;
import com.epam.au.service.validator.AddProductValidator;
import com.epam.au.service.validator.ConnectBankAccountValidator;
import com.epam.au.service.wrapper.HttpWrapper;
import com.sun.deploy.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

public class AddProductFormHandler implements FormHandler {
    private final Logger LOG = Logger.getLogger(AddProductFormHandler.class);

    private AddProductValidator validator;
    private ProductDataBaseDAO dao;

    public AddProductFormHandler() {
        validator = new AddProductValidator();
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (ProductDataBaseDAO) factory.create("product");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    @Override
    public boolean handle(HttpWrapper wrapper) {
        Product product = new Product();

        product.setName(wrapper.getRequestParameter("name"));
        product.setDescription(wrapper.getRequestParameter("description"));

        try {
            product.setAmount(Integer.parseInt(wrapper.getRequestParameter("amount")));
        } catch (NumberFormatException e) {
            LOG.error("Not a number, adding product", e);
            wrapper.addError("product.field.amount", "error.nan");
        }
        try {
            product.setPrice(Double.parseDouble(wrapper.getRequestParameter("price")));
        } catch (NumberFormatException e) {
            LOG.error("Not a number, adding product", e);
            wrapper.addError("product.field.price", "error.nan");
        }

        if (wrapper.getErrors().hasErrors()) {
            return false;
        }

        if (validator.validate(product)) {
            User user = (User) wrapper.getSessionAttribute("user");
            product.setUserId(user.getId());
            product.setStatus(ProductStatus.AVAILABLE);
            dao.create(product);

            return true;
        } else {
            wrapper.addErrors(validator.getErrors());
            return false;
        }
    }
}
