package controller.command;

public class IllegalCommandException extends Exception {
    /**
     * Default constructor.
     */
    public IllegalCommandException() {
        super("Undefined command");
    }

    /**
     * @param message
     */
    public IllegalCommandException(String message) {
        super(message);
    }
}
