package com.epam.au.service.listener;

import com.epam.au.service.handler.SAXHandler.RouteHandler;
import org.apache.log4j.Logger;
import com.epam.au.service.pool.ConnectionPool;
import com.epam.au.service.pool.ConnectionPoolException;
import org.xml.sax.SAXException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class ServletContextInitializer implements ServletContextListener {
    private final static Logger LOG = Logger.getLogger(ServletContextInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initializeConnectionPool();
        initializeRoutes();
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

    private void initializeRoutes() {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        ClassLoader classLoader = getClass().getClassLoader();
        RouteHandler routeHandler = new RouteHandler();
        try {
            SAXParser saxParser = parserFactory.newSAXParser();
            saxParser.parse(new File(classLoader.getResource("pages.xml").getFile()), routeHandler);
        } catch (ParserConfigurationException | SAXException e) {
            LOG.error("Indicated a serious configuration error.", e);
        } catch (IOException e) {
            LOG.error("Can't parse files.", e);
        }
    }

    private void closeConnectionPool() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.dispose();
    }
}