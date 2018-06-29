package dao.exception;

/**
 * This exception throws when providers do not find entity
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException() {
    }

    /**
     * @param message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
