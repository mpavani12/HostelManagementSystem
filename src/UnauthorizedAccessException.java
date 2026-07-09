package exceptions;

// MEMBER 4 - Staff Security Exception Handling
public class UnauthorizedAccessException extends Exception {

    // MEMBER 4 - Unauthorized complaint access exception
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}