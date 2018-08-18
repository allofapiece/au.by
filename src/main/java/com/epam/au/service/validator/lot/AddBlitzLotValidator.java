package com.epam.au.service.validator.lot;

import com.epam.au.entity.Product;
import com.epam.au.entity.lot.BlitzLot;
import com.epam.au.service.validator.ConnectBankAccountValidator;
import org.apache.log4j.Logger;

public class AddBlitzLotValidator extends AddLotValidator {
    private static final Logger LOG = Logger.getLogger(ConnectBankAccountValidator.class);

    public AddBlitzLotValidator() {
    }

    @Override
    public boolean validate(Object object) {
        BlitzLot lot = (BlitzLot) object;

        super.validate(lot);

        emptyValidate(
                lot.getOutgoingAmount(),
                "lot.field.outgoing",
                true
        );
        numberSizeValidate(
                lot.getOutgoingAmount(),
                1,
                "lot.field.outgoing",
                false
                );

        emptyValidate(
                lot.getRoundAmount(),
                "lot.field.round.amount",
                true
        );
        numberSizeValidate(
                lot.getRoundAmount(),
                1,
                10,
                "lot.field.round.amount",
                false
        );

        emptyValidate(
                lot.getRoundTime(),
                "lot.field.round.time",
                true
        );/*
        numberSizeValidate(
                lot.getRoundTime(),
                36000,
                1800000,
                "lot.field.round.time",
                false
        );*/

        emptyValidate(
                lot.getMinPeopleAmount(),
                "lot.field.min-people",
                true
        );
        numberSizeValidate(
                lot.getMinPeopleAmount(),
                100,
                "lot.field.min-people",
                false
        );

        emptyValidate(
                lot.getMaxPeopleAmount(),
                "lot.field.max-people",
                true
        );
        numberSizeValidate(
                lot.getMaxPeopleAmount(),
                100,
                "lot.field.max-people",
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
