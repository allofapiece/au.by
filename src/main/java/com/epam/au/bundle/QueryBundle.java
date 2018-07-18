package com.epam.au.bundle;

import java.util.ResourceBundle;

/**
 * Class is a com.epam.au.bundle for database static queries.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public abstract class QueryBundle {
    /**
     * This class don't have default constructor.
     */

    private ResourceBundle resourceBundle;

    /**
     * Constructor.
     * Sets com.epam.au.bundle by string.
     *
     * @param bundleName com.epam.au.bundle name
     */
    public QueryBundle(String bundleName) {
        setResourceBundle(ResourceBundle.getBundle(bundleName));
    }

    /**
     * Constructor.
     * Sets com.epam.au.bundle by prepared com.epam.au.bundle.
     *
     * @param resourceBundle prepared resource com.epam.au.bundle
     */
    public QueryBundle(ResourceBundle resourceBundle) {
        setResourceBundle(resourceBundle);
    }

    /**
     * Method return static database query for {@link com.epam.au.dao.DataBaseDAO Data Access Object}.
     *
     * @param queryName requested query
     * @return String static database query
     */
    public String getQuery(String queryName) {
        return resourceBundle.getString(queryName);
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}
