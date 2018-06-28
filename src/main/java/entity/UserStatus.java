package entity;

/**
 * Class says what state user have right now.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public enum UserStatus {
    /**
     * Active user has all common opportunities of using service.
     */
    ACTIVE,

    /**
     * Not active user can't use all necessary parts of service. Usually
     * inactive user, that did not confirm registration.
     */
    INACITVE,
}
