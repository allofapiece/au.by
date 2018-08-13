package com.epam.au.service.handler;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.EntityNotFoundException;
import com.epam.au.dao.impl.BieterDataBaseDAO;
import com.epam.au.dao.impl.LotDataBaseDAO;
import com.epam.au.dao.impl.ProductDataBaseDAO;
import com.epam.au.entity.*;
import com.epam.au.entity.lot.*;
import com.epam.au.exception.IllegalLotTypeException;
import com.epam.au.service.validator.AddBieterValidator;
import com.epam.au.service.validator.lot.AddBlitzLotValidator;
import com.epam.au.service.validator.lot.AddEnglishLotValidator;
import com.epam.au.service.validator.lot.AddInternetLotValidator;
import com.epam.au.service.validator.lot.AddLotValidator;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddBieterHandler implements FormHandler {
    private final Logger LOG = Logger.getLogger(AddBieterHandler.class);

    private AddBieterValidator validator;
    private BieterDataBaseDAO dao;

    public AddBieterHandler() {
        validator = new AddBieterValidator();
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (BieterDataBaseDAO) factory.create("bieter");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    @Override
    public boolean handle(HttpWrapper wrapper) {
        Bieter bieter = new Bieter();

        bieter.setUserId(wrapper.getUserId());
        bieter.setLotId(wrapper.getLong("bieter.field.id"));
        bieter.setJoinTime(new Timestamp(new Date().getTime()));

        if (validator.validate(bieter)) {
            dao.create(bieter);
            return true;
        } else {
            return false;
        }
    }
}
