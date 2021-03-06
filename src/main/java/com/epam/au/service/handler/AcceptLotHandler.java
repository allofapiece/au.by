package com.epam.au.service.handler;

import com.epam.au.dao.AbstractFactory;
import com.epam.au.dao.DataBaseDAOFactory;
import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.impl.LotDataBaseDAO;
import com.epam.au.entity.Role;
import com.epam.au.entity.User;
import com.epam.au.entity.lot.Lot;
import com.epam.au.entity.lot.LotStatus;
import com.epam.au.service.wrapper.HttpWrapper;
import org.apache.log4j.Logger;

public class AcceptLotHandler {
    private static final Logger LOG = Logger.getLogger(AcceptLotHandler.class);
    private LotDataBaseDAO dao;

    public AcceptLotHandler() {
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (LotDataBaseDAO) factory.create("lot");
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }
    }

    public boolean handle(HttpWrapper wrapper) {
        long lotId = wrapper.getLong("field.id");
        User user = wrapper.getUser();

        try {
            Lot lot = (Lot) dao.find(lotId);

            if (!user.hasRole(Role.ADMIN)) {
                wrapper.addError("page", "warning.reject.permission");

                return false;
            }

            if (lot.getStatus() == LotStatus.COMPLETED
                    || lot.getStatus() == LotStatus.CLOSED
                    || lot.getStatus() == LotStatus.STARTED) {
                wrapper.addError("page", "warning.reject.late");

                return false;
            }

            lot.setStatus(LotStatus.OPEN);
            lot.setMediatorId(user.getId());
            dao.update(lot);
        } catch (DAOException e) {
            LOG.error("DAO error", e);
        }

        return true;
    }
}
