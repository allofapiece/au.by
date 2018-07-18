package bundle;

/**
 * Class is a bundle for database static queries.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class AccountQueryBundle extends QueryBundle {
    private static volatile AccountQueryBundle instance;

    private AccountQueryBundle(String bundleName) {
        super(bundleName);
    }

    public static AccountQueryBundle getInstance() {
        if (instance == null)
            synchronized (AccountQueryBundle.class) {
                if (instance == null)
                    instance = new AccountQueryBundle(BundleNamesStore.BANK_ACCOUNT_QUERY_BUNDLE);
            }
        return instance;
    }
}
