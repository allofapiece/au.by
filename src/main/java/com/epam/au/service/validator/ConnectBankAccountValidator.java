package com.epam.au.service.validator;

import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.IllegalDataBaseDAOException;
import com.epam.au.dao.impl.BankAccountDataBaseDAO;
import com.epam.au.entity.Account;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ConnectBankAccountValidator extends Validator {
    private static final Logger LOG = Logger.getLogger(ConnectBankAccountValidator.class);
    private BankAccountDataBaseDAO dao;

    public ConnectBankAccountValidator() {
        DataBaseDAOFactory daoFactory = new DataBaseDAOFactory();
        try {
            dao = (BankAccountDataBaseDAO) daoFactory.create("account");
        } catch (IllegalDataBaseDAOException e) {
            LOG.error(e);
        }
    }

    @Override
    public boolean validate(Object object) {
        Account account = (Account) object;

        emptyValidate(account.getNumber(), "account.field.number", true);
        stringSizeValidate(
                account.getNumber(),
                14,
                17,
                "account.field.number",
                false
        );
        accountExistingValidate(
                account.getNumber(),
                "account.field.number",
                false
        );

        //TODO account already connect by another user validation

        return isValid();
    }

    public boolean accountExistingValidate(String number, String field, boolean isStacked) {
        List<String> messages = getErrors().getFieldErrors(field);

        if (!isStacked && messages != null) {
            return false;
        }

        Account account = dao.findByNumber(number);
        if (account == null) {
            setValid(false);

            if (messages == null) {
                messages = new ArrayList<>();
            }

            messages.add("error.exists");
            getErrors().setFieldErrors(field, messages);

            return false;
        }

        return true;
    }
}
