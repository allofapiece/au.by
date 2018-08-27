package com.epam.au.service.handler;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.impl.BetDataBaseDAO;
import com.epam.au.dao.impl.BieterDataBaseDAO;
import com.epam.au.entity.Bet;
import com.epam.au.entity.Bieter;
import com.epam.au.service.validator.AddBieterValidator;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Date;

public class AddBetHandler implements FormHandler {
    private final Logger LOG = Logger.getLogger(AddBetHandler.class);

    private AddBetValidator validator;
    private BetDataBaseDAO dao;

    public AddBetHandler() {
        validator = new AddBetValidator();
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (BetDataBaseDAO) factory.create("bet");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    @Override
    public boolean handle(HttpWrapper wrapper) {
        Bet bet = new Bet();

        bet.setLotId(wrapper.getLong("bieter.field.id"));
        bet.setUserId(wrapper.getUserId());
        bet.setPrice(wrapper.getLong("bieter.field.price"));

        if (wrapper.getErrors().hasErrors()) {
            return false;
        }

        if (validator.validate(bet)) {
            dao.create(bet);
            return true;
        } else {
            return false;
        }
    }
}
