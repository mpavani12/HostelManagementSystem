package util;

// MEMBER 1 - Unique ID Generation
public class IDGenerator {

    private static int studentCounter = 101;
    private static int staffCounter = 201;
    private static int complaintCounter = 1001;

    // MEMBER 1 - Student ID generation
    public static String generateStudentId() {
        return "ST" + studentCounter++;
    }

    // MEMBER 1 - Staff ID generation
    public static String generateStaffId() {
        return "SF" + staffCounter++;
    }

    // MEMBER 1 - Complaint ID generation
    public static String generateComplaintId() {
        return "C" + complaintCounter++;
    }
}