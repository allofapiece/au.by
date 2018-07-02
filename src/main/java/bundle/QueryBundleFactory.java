package bundle;

import bundle.exception.IllegalQueryBundleException;

public class QueryBundleFactory {

    public QueryBundle create(String queryBundle) throws IllegalQueryBundleException {
        switch (queryBundle) {
            case "user":
                return UserQueryBundle.getInstance();

            case "product":
                return ProductQueryBundle.getInstance();

            default:
                throw new IllegalQueryBundleException();
        }
    }
}
