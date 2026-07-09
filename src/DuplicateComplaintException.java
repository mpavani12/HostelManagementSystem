package exceptions;

// MEMBER 2 - Complaint Validation Exception Handling
public class DuplicateComplaintException extends Exception {

    // MEMBER 2 - Duplicate complaint exception
    public DuplicateComplaintException(String message) {
        super(message);
    }
}