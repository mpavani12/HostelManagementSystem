package model;

// MEMBER 2 - Student Complaint Lifecycle
public class Complaint {

    private String complaintId;
    private String studentId;
    private String studentName;
    private String roomNumber;
    private String category;
    private String title;
    private String description;
    private ComplaintStatus status;
    private String assignedStaffId;
    private String assignedStaffName;
    private String progressNote;

    // MEMBER 2 - Complaint object creation
    public Complaint(String complaintId, String studentId, String studentName,
                     String roomNumber, String category,
                     String title, String description) {
        this.complaintId = complaintId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.title = title;
        this.description = description;
        this.status = ComplaintStatus.PENDING;
        this.assignedStaffId = "";
        this.assignedStaffName = "";
        this.progressNote = "";
    }

    // MEMBER 2 - Complaint ID access
    public String getComplaintId() {
        return complaintId;
    }

    // MEMBER 2 - Student ID access
    public String getStudentId() {
        return studentId;
    }

    // MEMBER 2 - Student name access
    public String getStudentName() {
        return studentName;
    }

    // MEMBER 2 - Room number access
    public String getRoomNumber() {
        return roomNumber;
    }

    // MEMBER 2 - Complaint category access
    public String getCategory() {
        return category;
    }

    // MEMBER 2 - Complaint title access
    public String getTitle() {
        return title;
    }

    // MEMBER 2 - Complaint description access
    public String getDescription() {
        return description;
    }

    // MEMBER 2 - Complaint status access
    public ComplaintStatus getStatus() {
        return status;
    }

    // MEMBER 2 - Complaint status update
    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    // MEMBER 2 - Assigned staff ID access
    public String getAssignedStaffId() {
        return assignedStaffId;
    }

    // MEMBER 2 - Assigned staff ID update
    public void setAssignedStaffId(String assignedStaffId) {
        this.assignedStaffId = assignedStaffId;
    }

    // MEMBER 2 - Assigned staff name access
    public String getAssignedStaffName() {
        return assignedStaffName;
    }

    // MEMBER 2 - Assigned staff name update
    public void setAssignedStaffName(String assignedStaffName) {
        this.assignedStaffName = assignedStaffName;
    }

    // MEMBER 2 - Progress note access
    public String getProgressNote() {
        return progressNote;
    }

    // MEMBER 2 - Progress note update
    public void setProgressNote(String progressNote) {
        this.progressNote = progressNote;
    }
}