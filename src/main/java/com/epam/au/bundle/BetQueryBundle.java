package com.epam.au.bundle;

/**
 * Class is a com.epam.au.bundle for database static queries.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class BetQueryBundle extends QueryBundle {
    private static volatile BetQueryBundle instance;

    private BetQueryBundle(String bundleName) {
        super(bundleName);
    }

    public static BetQueryBundle getInstance() {
        if (instance == null)
            synchronized (BetQueryBundle.class) {
                if (instance == null)
                    instance = new BetQueryBundle(BundleNamesStore.BET_QUERY_BUNDLE);
            }
        return instance;
    }
}
