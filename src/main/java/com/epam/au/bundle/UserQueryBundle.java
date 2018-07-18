package com.epam.au.bundle;

/**
 * Class is a com.epam.au.bundle for database static queries.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class UserQueryBundle extends QueryBundle {
    private static volatile UserQueryBundle instance;

    private UserQueryBundle(String bundleName) {
        super(bundleName);
    }

    public static UserQueryBundle getInstance() {
        if (instance == null)
            synchronized (UserQueryBundle.class) {
                if (instance == null)
                    instance = new UserQueryBundle(BundleNamesStore.USER_QUERY_BUNDLE);
            }
        return instance;
    }
}
