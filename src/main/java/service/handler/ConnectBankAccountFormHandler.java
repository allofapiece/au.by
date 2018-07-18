package service.handler;

import dao.AbstractFactory;
import dao.DataBaseDAOFactory;
import dao.exception.DAOException;
import dao.impl.BankAccountDataBaseDAO;
import dao.impl.UserDataBaseDAO;
import entity.Account;
import entity.User;
import org.apache.log4j.Logger;
import service.validator.ConnectBankAccountValidator;
import service.validator.Errors;
import service.validator.SignInUserValidator;
import service.wrapper.HttpWrapper;

public class ConnectBankAccountFormHandler implements FormHandler {
    private final Logger LOG = Logger.getLogger(ConnectBankAccountFormHandler.class);

    private ConnectBankAccountValidator validator;
    private BankAccountDataBaseDAO dao;
    private UserDataBaseDAO userDAO;

    public ConnectBankAccountFormHandler() {
        validator = new ConnectBankAccountValidator();
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (BankAccountDataBaseDAO) factory.create("account");
            userDAO = (UserDataBaseDAO) factory.create("user");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    @Override
    public boolean handle(HttpWrapper wrapper) {
        Account account = new Account();
        account.setNumber(wrapper.getRequestParameter("account-number"));

        if (validator.validate(account)) {
            User user = (User) wrapper.getSessionAttribute("user");
            account = dao.findByNumber(account.getNumber());

            try {
                user.setAccount(account);
                userDAO.update(user);
            } catch (DAOException e) {
                LOG.error("Updating user account error", e);
            }

            wrapper.addSessionAttribute("user", user);

            return true;
        } else {
            wrapper.addErrors(validator.getErrors());
            return false;
        }
    }
}
