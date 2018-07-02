package bundle;

/**
 * Class is a bundle for database static queries.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class ProductQueryBundle extends QueryBundle {
    private static volatile ProductQueryBundle instance;

    private ProductQueryBundle(String bundleName) {
        super(bundleName);
    }

    public static ProductQueryBundle getInstance() {
        if (instance == null)
            synchronized (ProductQueryBundle.class) {
                if (instance == null)
                    instance = new ProductQueryBundle(BundleNamesStore.PRODUCT_QUERY_BUNDLE);
            }
        return instance;
    }
}
