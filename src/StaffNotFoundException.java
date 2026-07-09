package exceptions;

// MEMBER 1 - Admin Staff Management Exception Handling
public class StaffNotFoundException extends Exception {

    // MEMBER 1 - Invalid staff lookup exception
    public StaffNotFoundException(String message) {
        super(message);
    }
}