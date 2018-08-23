package com.epam.au.dao;

import com.epam.au.dao.exception.DAOException;
import com.epam.au.dao.exception.IllegalDAOTypeException;
import com.epam.au.dao.exception.IllegalDataBaseDAOException;
import com.epam.au.dao.impl.UserDataBaseDAO;
import com.epam.au.entity.Role;
import com.epam.au.entity.User;
import com.epam.au.entity.UserStatus;
import com.epam.au.service.pool.ConnectionPool;
import com.epam.au.service.pool.ConnectionPoolException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDataBaseDAOTest {
    private UserDataBaseDAO dao;

    private User signUpUser;
    private User signInUser;

    @BeforeClass
    public static void initConnectionPool() throws ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
    }

    @Before
    public void setUp() throws DAOException {
        DataBaseDAOFactory factory;

        factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
        dao = (UserDataBaseDAO) factory.create("user");

        signUpUser = new User();
        signInUser = new User();

        signUpUser.setName("Grisha");
        signUpUser.setSurname("Ficus");
        signUpUser.setPassword("qqq111");
        signUpUser.setSalt("11");
        signUpUser.setEmail("allofapiece@gmail.com");
        signUpUser.setStatus(UserStatus.ACTIVE);
        signUpUser.addRole(Role.USER);

        signInUser.setEmail("allofapiece@gmail.com");
        signInUser.setPassword("qqq111");
    }

    @Test
    public void createUserShouldReturnInsertedEntity() throws IllegalDataBaseDAOException, IllegalDAOTypeException {
        dao.create(signUpUser);

        User expectedUser = dao.findByEmail("allofapiece@gmail.com");

        Assert.assertEquals(expectedUser, signUpUser);
    }
}
