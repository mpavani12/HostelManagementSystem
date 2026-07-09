package exceptions;

// MEMBER 1 - User Management Exception Handling
public class DuplicateUserException extends Exception {

    // MEMBER 1 - Duplicate username exception
    public DuplicateUserException(String message) {
        super(message);
    }
}