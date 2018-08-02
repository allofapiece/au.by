package com.epam.au.service.handler;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.EntityNotFoundException;
import com.epam.au.dao.impl.LotDataBaseDAO;
import com.epam.au.dao.impl.ProductDataBaseDAO;
import com.epam.au.entity.*;
import com.epam.au.entity.lot.*;
import com.epam.au.exception.IllegalLotTypeException;
import com.epam.au.service.validator.lot.AddBlitzLotValidator;
import com.epam.au.service.validator.lot.AddEnglishLotValidator;
import com.epam.au.service.validator.lot.AddInternetLotValidator;
import com.epam.au.service.validator.lot.AddLotValidator;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

public class AddLotFormHandler implements FormHandler {
    private final Logger LOG = Logger.getLogger(AddLotFormHandler.class);

    private AddLotValidator validator;
    private LotDataBaseDAO dao;
    private ProductDataBaseDAO productDAO;

    public AddLotFormHandler() {
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (LotDataBaseDAO) factory.create("lot");
            productDAO = (ProductDataBaseDAO) factory.create("product");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    @Override
    public boolean handle(HttpWrapper wrapper) {
        Lot lot;
        String type = wrapper.getRequestParameter("type");
        try {
            lot = lotStrategy(type);
        } catch (IllegalLotTypeException e) {
            LOG.error("Undefined type of lot");
            wrapper.addError("lot.field.type", "error.illegal");
            return false;
        }

        lot.setName(wrapper.getRequestParameter("name"));
        lot.setDescription(wrapper.getRequestParameter("description"));
        lot.setAuctionType(AuctionType.valueOf(type.toUpperCase()));
        Product product = null ;
        try {
            product = productDAO.findForUser(
                    wrapper.getLong("lot.field.product-id"),
                    (User) wrapper.getSessionAttribute("user")
            );
            if (product == null) {
                wrapper.addError("lot.field,product-id", "error.not_exists");
            }
        } catch (EntityNotFoundException e) {
            LOG.error("Entity not found", e);
        }
        lot.setProduct(product);
        lot.setProductAmount(wrapper.getInt("lot.field.amount"));
        lot.setBeginPrice(wrapper.getDouble("lot.field.begin-price"));

        switch (type) {
            case "blitz":
                ((BlitzLot) lot).setOutgoingAmount(wrapper.getInt("lot.field.outgoing"));
                ((BlitzLot) lot).setRoundAmount(wrapper.getInt("lot.field.round.amount", "round-amount"));
                ((BlitzLot) lot).setRoundTime(wrapper.getLong("lot.field.round.time", "round-time"));
                ((BlitzLot) lot).setMinPeopleAmount(
                        !wrapper.getRequestParameter("min-people").equals("")
                        ? wrapper.getInt("lot.field.min-people")
                        : 0
                );
                ((BlitzLot) lot).setMaxPeopleAmount(
                        !wrapper.getRequestParameter("max-people").equals("")
                        ? wrapper.getInt("lot.field.max-people")
                        : 0
                );
                ((BlitzLot) lot).setBlitzPrice(
                        !wrapper.getRequestParameter("blitz-price").equals("")
                                ? wrapper.getInt("lot.field.blitz-price")
                                : 0
                );
                break;

            case "english":
                ((EnglishLot) lot).setBetTime(wrapper.getLong("lot.field.bet-time"));
                ((EnglishLot) lot).setStepPrice(wrapper.getDouble("lot.field.step-price"));
                break;

            case "internet":
                ((InternetLot) lot).setBetTime(wrapper.getLong("lot.field.bet-time"));
                ((InternetLot) lot).setBlitzPrice(wrapper.getDouble("lot.field.blitz-price"));
                break;
        }

        if (wrapper.getErrors().hasErrors()) {
            return false;
        }

        if (validator.validate(lot)) {
            User user = (User) wrapper.getSessionAttribute("user");
            lot.setSellerId(user.getId());
            lot.setStatus(LotStatus.PROPOSED);
            dao.create(lot);

            return true;
        } else {
            wrapper.addErrors(validator.getErrors());
            return false;
        }
    }

    private Lot lotStrategy(String type) throws IllegalLotTypeException {
        switch (type) {
            case "blitz":
                validator = new AddBlitzLotValidator();
                return new BlitzLot();

            case "english":
                validator = new AddEnglishLotValidator();
                return new EnglishLot();

            case "internet":
                validator = new AddInternetLotValidator();
                return new InternetLot();

            default:
                throw new IllegalLotTypeException();
        }
    }
}
