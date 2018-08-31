package com.epam.au.service.validator;

import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.IllegalDataBaseDAOException;
import com.epam.au.dao.impl.BetDataBaseDAO;
import com.epam.au.dao.impl.LotDataBaseDAO;
import com.epam.au.entity.Bet;
import com.epam.au.entity.Bieter;
import com.epam.au.entity.lot.Lot;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddBetValidator extends Validator {
    private static final Logger LOG = Logger.getLogger(ConnectBankAccountValidator.class);

    private BetDataBaseDAO betDAO;
    private LotDataBaseDAO lotDAO;
    private List<Bet> bets;

    public AddBetValidator() {
        DataBaseDAOFactory daoFactory = new DataBaseDAOFactory();
        try {
            betDAO = (BetDataBaseDAO) daoFactory.create("bet");
            lotDAO = (LotDataBaseDAO) daoFactory.create("lot");
        } catch (IllegalDataBaseDAOException e) {
            LOG.error(e);
        }
    }

    @Override
    public boolean validate(Object object) {
        Bet bet = (Bet) object;

        try {
            bets = betDAO.findByLotId(bet.getLotId());
            bets.sort((lhs, rhs) -> lhs.getTime().after(rhs.getTime()) ? -1 : (lhs.getTime().before(rhs.getTime())) ? 1 : 0);
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }

        emptyValidate(bet.getPrice(), "bet.field.price", true);
        numberSizeValidate(bet.getPrice(), 1, 10000, "bet.field.price", false);

        lastBieterValidate(bet.getUserId(), "bet.field.bieter");

        priceGreaterThanStartPrice(bet, "bet.field.price");

        if (isValid()) {
            priceGreaterThanLastValidate(bet.getPrice(), "bet.field.price");
        }

        return isValid();
    }

    public boolean lastBieterValidate(long userId, String field) {
        boolean v = true;
        List<String> messages = getErrors().getFieldErrors(field);

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (bets == null || bets.size() == 0) {
            return true;
        }

        if (bets.get(0).getUserId() == userId) {
            messages.add("error.last-bet");
            v = false;
            setValid(v);
        }

        getErrors().setFieldErrors(field, messages);

        return v;
    }

    public boolean priceGreaterThanLastValidate(double price, String field) {
        boolean v = true;
        List<String> messages = getErrors().getFieldErrors(field);

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (bets == null || bets.size() == 0) {
            return true;
        }

        if (bets.get(0).getPrice() >= price) {
            messages.add("error.little-bet");
            v = false;
            setValid(v);
        }

        getErrors().setFieldErrors(field, messages);

        return v;
    }

    public boolean priceGreaterThanStartPrice(Bet bet, String field) {
        boolean v = true;
        List<String> messages = getErrors().getFieldErrors(field);

        if (messages == null) {
            messages = new ArrayList<>();
        }

        Lot lot = null;

        try {
            lot = (Lot) lotDAO.find(bet.getLotId());
        } catch (DAOException e) {
            LOG.error(e);
        }

        if (lot == null || bet.getPrice() <= lot.getBeginPrice()) {
            messages.add("error.begin-price.tight");
            v = false;
            setValid(v);
        }

        getErrors().setFieldErrors(field, messages);

        return v;
    }
}
