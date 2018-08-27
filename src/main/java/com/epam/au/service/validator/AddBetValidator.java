package com.epam.au.service.validator;

import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.IllegalDataBaseDAOException;
import com.epam.au.dao.impl.LotDataBaseDAO;
import com.epam.au.entity.Bieter;
import com.epam.au.entity.lot.Lot;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AddBetValidator extends Validator {
    private static final Logger LOG = Logger.getLogger(ConnectBankAccountValidator.class);

    private LotDataBaseDAO lotDAO;

    public AddBetValidator() {
        DataBaseDAOFactory daoFactory = new DataBaseDAOFactory();
        try {
            lotDAO = (LotDataBaseDAO) daoFactory.create("lot");
        } catch (IllegalDataBaseDAOException e) {
            LOG.error(e);
        }
    }

    @Override
    public boolean validate(Object object) {
        Bieter bieter = (Bieter) object;

        userExistValidate(bieter.getUserId(), "user.field.id");

        lotExistsValidate(bieter.getLotId(), "lot.field.id");

        return isValid();
    }

    public boolean lotExistsValidate(long id, String field) {
        boolean v = true;
        List<String> messages = getErrors().getFieldErrors(field);
        Lot lot = null;

        if (messages == null) {
            messages = new ArrayList<>();
        }

        try {
            lot = (Lot) lotDAO.find(id);
            if (lot == null) {
                messages.add("error.nexists");
                v = false;
                setValid(v);
            }
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }

        return v;
    }
}
