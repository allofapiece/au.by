package com.epam.au.bundle;

/**
 * Class is a com.epam.au.bundle for database static queries.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class BieterQueryBundle extends QueryBundle {
    private static volatile BieterQueryBundle instance;

    private BieterQueryBundle(String bundleName) {
        super(bundleName);
    }

    public static BieterQueryBundle getInstance() {
        if (instance == null)
            synchronized (BieterQueryBundle.class) {
                if (instance == null)
                    instance = new BieterQueryBundle(BundleNamesStore.BIETER_QUERY_BUNDLE);
            }
        return instance;
    }
}
