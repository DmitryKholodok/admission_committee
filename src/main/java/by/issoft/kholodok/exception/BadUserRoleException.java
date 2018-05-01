package by.issoft.kholodok.exception;

public class BadUserRoleException extends Exception {

    public BadUserRoleException() {
        super();
    }

    public BadUserRoleException(String message) {
        super(message);
    }

    public BadUserRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadUserRoleException(Throwable cause) {
        super(cause);
    }
}
