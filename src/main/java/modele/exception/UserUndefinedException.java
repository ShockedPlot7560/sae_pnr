package modele.exception;

public class UserUndefinedException extends LoginException {
    public UserUndefinedException(String message) {
        super(message);
    }
}
