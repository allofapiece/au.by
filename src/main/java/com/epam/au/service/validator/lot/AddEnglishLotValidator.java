package com.epam.au.service.validator.lot;

import com.epam.au.entity.Product;
import com.epam.au.entity.lot.EnglishLot;
import com.epam.au.service.validator.ConnectBankAccountValidator;
import org.apache.log4j.Logger;

public class AddEnglishLotValidator extends AddLotValidator {
    private static final Logger LOG = Logger.getLogger(ConnectBankAccountValidator.class);

    public AddEnglishLotValidator() {
    }

    @Override
    public boolean validate(Object object) {
        EnglishLot lot = (EnglishLot) object;

        super.validate(lot);

        emptyValidate(
                lot.getBetTime(),
                "lot.field.bet-time",
                true
        );
        numberSizeValidate(
                lot.getBetTime(),
                30000,
                1800000,
                "lot.field.bet-time",
                false
        );

        emptyValidate(lot.getStepPrice(), "lot.field.step-price", true);
        numberSizeValidate(
                lot.getStepPrice(),
                0.01,
                999999,
                "lot.field.step-price",
                false
        );

        return isValid();
    }
}
