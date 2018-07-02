package bundle.exception;

public class IllegalQueryBundleException extends Exception {
    /**
     * Default constructor.
     */
    public IllegalQueryBundleException() {
        super("Undefined query bundle");
    }

    /**
     * @param message
     */
    public IllegalQueryBundleException(String message) {
        super(message);
    }
}
