package com.epam.au.bundle;

/**
 * Class is a com.epam.au.bundle for database static queries.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class LotQueryBundle extends QueryBundle {
    private static volatile LotQueryBundle instance;

    private LotQueryBundle(String bundleName) {
        super(bundleName);
    }

    public static LotQueryBundle getInstance() {
        if (instance == null)
            synchronized (LotQueryBundle.class) {
                if (instance == null)
                    instance = new LotQueryBundle(BundleNamesStore.LOT_QUERY_BUNDLE);
            }
        return instance;
    }
}
