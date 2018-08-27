package com.epam.au.service.validator.lot;

import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.IllegalDataBaseDAOException;
import com.epam.au.dao.impl.BankAccountDataBaseDAO;
import com.epam.au.dao.impl.ProductDataBaseDAO;
import com.epam.au.entity.Product;
import com.epam.au.entity.lot.Lot;
import com.epam.au.service.validator.Validator;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

public abstract class AddLotValidator extends Validator {
    private static final Logger LOG = Logger.getLogger(AddLotValidator.class);
    private ProductDataBaseDAO productDAO;

    public AddLotValidator() {
        DataBaseDAOFactory daoFactory = new DataBaseDAOFactory();
        try {
            productDAO = (ProductDataBaseDAO) daoFactory.create("product");
        } catch (IllegalDataBaseDAOException e) {
            LOG.error(e);
        }
    }

    @Override
    public boolean validate(Object object) {
        Lot lot = (Lot) object;

        emptyValidate(lot.getName(), "lot.field.name", true);
        stringSizeValidate(lot.getName(), 2, 100, "lot.field.name", false);

        emptyValidate(lot.getDescription(), "lot.field.description", true);
        stringSizeValidate(lot.getDescription(), 2, 100, "lot.field.description", false);

        //TODO selectValidate for auction type

        emptyValidate(lot.getProductAmount(), "lot.field.amount", true);
        numberSizeValidate(
                lot.getProductAmount(),
                1,
                lot.getProduct().getAmount(),
                "lot.field.amount",
                false
        );

        emptyValidate(lot.getBeginPrice(), "lot.field.begin-price", true);
        numberSizeValidate(
                lot.getBeginPrice(),
                0.01,
                999999,
                "lot.field.begin-price",
                false
        );

        emptyValidate(lot.getStartTime(), "lot.field.start-time", true);
        timeValidate(lot.getStartTime(), new Date(), 86400000, true, "lot.field.start-time", false);

        return isValid();
    }
}
