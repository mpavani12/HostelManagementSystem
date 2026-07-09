package exceptions;

// MEMBER 2 - Complaint Lookup Exception Handling
public class ComplaintNotFoundException extends Exception {

    // MEMBER 2 - Complaint not found exception
    public ComplaintNotFoundException(String message) {
        super(message);
    }
}