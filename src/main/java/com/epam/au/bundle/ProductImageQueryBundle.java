package com.epam.au.bundle;

/**
 * Class is a com.epam.au.bundle for database static queries.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class ProductImageQueryBundle extends QueryBundle {
    private static volatile ProductImageQueryBundle instance;

    private ProductImageQueryBundle(String bundleName) {
        super(bundleName);
    }

    public static ProductImageQueryBundle getInstance() {
        if (instance == null)
            synchronized (ProductImageQueryBundle.class) {
                if (instance == null)
                    instance = new ProductImageQueryBundle(BundleNamesStore.PRODUCT_IMAGE_QUERY_BUNDLE);
            }
        return instance;
    }
}
