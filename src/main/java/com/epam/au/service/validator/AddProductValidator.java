package com.epam.au.service.validator;

import com.epam.au.entity.Product;
import org.apache.log4j.Logger;

public class AddProductValidator extends Validator {
    private static final Logger LOG = Logger.getLogger(ConnectBankAccountValidator.class);

    public AddProductValidator() {
    }

    @Override
    public boolean validate(Object object) {
        Product product = (Product) object;

        emptyValidate(product.getName(), "product.field.name", true);
        stringSizeValidate(
                product.getName(),
                3,
                30,
                "product.field.name",
                false
        );

        emptyValidate(product.getDescription(), "product.field.description", true);
        stringSizeValidate(
                product.getDescription(),
                3,
                200,
                "product.field.description",
                false
        );

        emptyValidate(product.getAmount(), "product.field.amount", true);
        numberSizeValidate(
                product.getAmount(),
                1,
                999999,
                "product.field.amount",
                false
        );

        emptyValidate(product.getPrice(), "product.field.price", true);
        numberSizeValidate(
                product.getAmount(),
                0.01,
                999999,
                "product.field.price",
                false
        );

        return isValid();
    }
}
