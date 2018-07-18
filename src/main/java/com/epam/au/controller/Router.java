package com.epam.au.controller;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private static volatile Router instance;
    private Map<String, Page> router;

    private Router() {
        router = new HashMap<>();
    }

    public static Router getInstance() {
        if (instance == null)
            synchronized (Router.class) {
                if (instance == null)
                    instance = new Router();
            }
        return instance;
    }

    public Page getPage(String route) {
        return router.get(route);
    }

    public void setRoute(String route, Page page) {
        router.put(route, page);
    }
}
