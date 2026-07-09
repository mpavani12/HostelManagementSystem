package exceptions;

// MEMBER 1 - Authentication Exception Handling
public class InvalidLoginException extends Exception {

    // MEMBER 1 - Invalid credential exception
    public InvalidLoginException(String message) {
        super(message);
    }
}