package com.epam.au.bundle;

import com.epam.au.bundle.exception.IllegalQueryBundleException;

public class QueryBundleFactory {

    public QueryBundle create(String queryBundle) throws IllegalQueryBundleException {
        switch (queryBundle) {
            case "user":
                return UserQueryBundle.getInstance();

            case "product":
                return ProductQueryBundle.getInstance();

            case "account":
                return AccountQueryBundle.getInstance();

            case "product-image":
                return ProductImageQueryBundle.getInstance();

            case "lot":
                return LotQueryBundle.getInstance();

            case "bet":
                return BetQueryBundle.getInstance();

            case "bieter":
                return BieterQueryBundle.getInstance();

            case "event":
                return EventQueryBundle.getInstance();

            default:
                throw new IllegalQueryBundleException();
        }
    }
}
