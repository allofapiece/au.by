package com.epam.au.bundle;

/**
 * Class is a com.epam.au.bundle for database static queries.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class EventQueryBundle extends QueryBundle {
    private static volatile EventQueryBundle instance;

    private EventQueryBundle(String bundleName) {
        super(bundleName);
    }

    public static EventQueryBundle getInstance() {
        if (instance == null)
            synchronized (EventQueryBundle.class) {
                if (instance == null)
                    instance = new EventQueryBundle(BundleNamesStore.EVENT_QUERY_BUNDLE);
            }
        return instance;
    }
}
