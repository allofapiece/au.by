package bundle;

import java.util.ResourceBundle;

/**
 * Class is a bundle for database static queries.
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
     * Sets bundle by string.
     *
     * @param bundleName bundle name
     */
    public QueryBundle(String bundleName) {
        setResourceBundle(ResourceBundle.getBundle(bundleName));
    }

    /**
     * Constructor.
     * Sets bundle by prepared bundle.
     *
     * @param resourceBundle prepared resource bundle
     */
    public QueryBundle(ResourceBundle resourceBundle) {
        setResourceBundle(resourceBundle);
    }

    /**
     * Method return static database query for {@link dao.DataBaseDAO Data Access Object}.
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
