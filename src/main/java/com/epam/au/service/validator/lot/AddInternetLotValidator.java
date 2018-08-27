package com.epam.au.service.validator.lot;

import com.epam.au.entity.Product;
import com.epam.au.entity.lot.InternetLot;
import com.epam.au.service.validator.ConnectBankAccountValidator;
import org.apache.log4j.Logger;

import java.sql.Time;

public class AddInternetLotValidator extends AddLotValidator {
    private static final Logger LOG = Logger.getLogger(ConnectBankAccountValidator.class);

    public AddInternetLotValidator() {
    }

    @Override
    public boolean validate(Object object) {
        InternetLot lot = (InternetLot) object;

        super.validate(lot);

        emptyValidate(
                lot.getBetTime(),
                "lot.field.bet-time",
                true
        );
        numberSizeValidate(
                lot.getBetTime(),
                5,
                1200,
                "lot.field.bet-time",
                false
        );

        emptyValidate(lot.getBlitzPrice(), "lot.field.blitz-price", true);
        numberSizeValidate(
                lot.getBlitzPrice(),
                0.01,
                999999,
                "lot.field.blitz-price",
                false
        );

        return isValid();
    }
}
