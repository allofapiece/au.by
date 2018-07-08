package service.listener;

import org.apache.log4j.Logger;
import service.pool.ConnectionPool;
import service.pool.ConnectionPoolException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextInitializer implements ServletContextListener {
    private final static Logger LOG = Logger.getLogger(ServletContextInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initializeConnectionPool();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        closeConnectionPool();
    }

    private void initializeConnectionPool() {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            LOG.fatal("Fatal error while initializing connection pool", e);
        }
    }

    private void closeConnectionPool() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.dispose();
    }
}