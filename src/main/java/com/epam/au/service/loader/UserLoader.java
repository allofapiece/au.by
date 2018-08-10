package com.epam.au.service.loader;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.impl.LotDataBaseDAO;
import com.epam.au.dao.impl.UserDataBaseDAO;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.Lot;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

import java.util.List;

public class UserLoader implements Loader {
    private static final Logger LOG = Logger.getLogger(UserLoader.class);
    private UserDataBaseDAO dao;

    public UserLoader() {
        DataBaseDAOFactory factory;
        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (UserDataBaseDAO) factory.create("user");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    public User load(long id) {
        User user = null;
        try {
            user = dao.find(id);
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }

        return user;
    }

    public List<Lot> loadByUser(HttpWrapper wrapper) {
        //TODO throwing unsupported exceptioin
        return null;
    }

    public List<Lot> loadAll(HttpWrapper wrapper) {
        //TODO throwing unsupported exceptioin
        return null;
    }
}
