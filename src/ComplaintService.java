package service;

import java.util.ArrayList;

import exceptions.ComplaintNotFoundException;
import exceptions.DuplicateComplaintException;
import exceptions.InvalidComplaintStateException;
import exceptions.UnauthorizedAccessException;
import interfaces.Trackable;
import model.Complaint;
import model.ComplaintStatus;
import model.Staff;
import model.Student;
import util.ConsoleColors;
import util.ConsoleUI;

// MEMBER 2 - Student Complaint Lifecycle
public class ComplaintService implements Trackable {

    private ArrayList<Complaint> complaints;

    // MEMBER 2 - Complaint storage initialization
    public ComplaintService() {
        complaints = new ArrayList<>();
    }

    // MEMBER 2 - Raise complaint with duplicate validation
    public void raiseComplaint(Student student, String category,
            String title, String description,
            String complaintId) throws DuplicateComplaintException {

        for (Complaint existingComplaint : complaints) {
            if (existingComplaint.getStudentId().equals(student.getId())
                    && existingComplaint.getRoomNumber().equals(student.getRoomNumber())
                    && existingComplaint.getCategory().equalsIgnoreCase(category)
                    && existingComplaint.getStatus() != ComplaintStatus.CLOSED) {

                throw new DuplicateComplaintException(
                        "Active complaint already exists for this category in your room."
                );
            }
        }

        Complaint complaint = new Complaint(
                complaintId,
                student.getId(),
                student.getName(),
                student.getRoomNumber(),
                category,
                title,
                description
        );

        complaints.add(complaint);
    }

    // MEMBER 2 - View student complaints
    public ArrayList<Complaint> viewStudentComplaints(Student student) {
        ArrayList<Complaint> result = new ArrayList<>();

        for (Complaint complaint : complaints) {
            if (complaint.getStudentId().equals(student.getId())) {
                result.add(complaint);
            }
        }

        return result;
    }

    // MEMBER 2 - Track complaint status
    @Override
    public void trackStatus(String complaintId) {

        for (Complaint complaint : complaints) {

            if (complaint.getComplaintId().equalsIgnoreCase(complaintId)) {

                ConsoleUI.printHeader("COMPLAINT STATUS TRACKER");

                System.out.println(
                        ConsoleColors.BRIGHT_YELLOW +
                        "╔════════════════════╦════════════════════════════════════════════╗"
                );

                System.out.printf(
                        "║ %-18s ║ %-42s ║%n",
                        "COMPLAINT ID",
                        complaint.getComplaintId()
                );

                System.out.println(
                        "╠════════════════════╬════════════════════════════════════════════╣"
                );

                System.out.printf(
                        "║ %-18s ║ %-42s ║%n",
                        "TITLE",
                        complaint.getTitle()
                );

                System.out.println(
                        "╠════════════════════╬════════════════════════════════════════════╣"
                );

                System.out.printf(
                        "║ %-18s ║ %-42s ║%n",
                        "STATUS",
                        complaint.getStatus()
                );

                System.out.println(
                        "╠════════════════════╬════════════════════════════════════════════╣"
                );

                System.out.printf(
                        "║ %-18s ║ %-42s ║%n",
                        "ASSIGNED STAFF",
                        complaint.getAssignedStaffName() == null
                                ? "Not Assigned Yet"
                                : complaint.getAssignedStaffName()
                );

                System.out.println(
                        "╠════════════════════╬════════════════════════════════════════════╣"
                );

                System.out.printf(
                        "║ %-18s ║ %-42s ║%n",
                        "PROGRESS NOTE",
                        complaint.getProgressNote() == null
                                ? "No updates yet"
                                : complaint.getProgressNote()
                );

                System.out.println(
                        "╚════════════════════╩════════════════════════════════════════════╝"
                        + ConsoleColors.RESET
                );

                return;
            }
        }

        ConsoleUI.printError("Complaint not found.");
    }

    // MEMBER 2 - Update complaint progress
    @Override
    public void updateProgress(String complaintId, Staff currentStaff, String note)
            throws ComplaintNotFoundException,
            UnauthorizedAccessException {

        Complaint complaint = findComplaintById(complaintId);

        if (!complaint.getAssignedStaffId().equals(currentStaff.getId())) {
            throw new UnauthorizedAccessException(
                    "This complaint is not assigned to you."
            );
        }

        complaint.setProgressNote(note);
        complaint.setStatus(ComplaintStatus.IN_PROGRESS);
    }

    // MEMBER 2 - Mark complaint resolved
    public void markResolved(String complaintId, Staff currentStaff, String resolutionNote)
            throws ComplaintNotFoundException,
            UnauthorizedAccessException {

        Complaint complaint = findComplaintById(complaintId);

        if (!complaint.getAssignedStaffId().equals(currentStaff.getId())) {
            throw new UnauthorizedAccessException("This complaint is not assigned to you.");
        }

        complaint.setStatus(ComplaintStatus.RESOLVED);
        complaint.setProgressNote(resolutionNote);
    }

    // MEMBER 2 - Close resolved complaint
    public void closeComplaint(String complaintId, Student student)
            throws ComplaintNotFoundException,
            InvalidComplaintStateException {

        Complaint complaint = findComplaintById(complaintId);

        if (!complaint.getStudentId().equals(student.getId())) {
            throw new InvalidComplaintStateException(
                    "You cannot close another student's complaint."
            );
        }

        if (complaint.getStatus() != ComplaintStatus.RESOLVED) {
            throw new InvalidComplaintStateException(
                    "Only resolved complaints can be closed."
            );
        }

        complaint.setStatus(ComplaintStatus.CLOSED);
    }

    // MEMBER 2 - View assigned complaints
    public ArrayList<Complaint> viewAssignedComplaints(Staff staff) {
        ArrayList<Complaint> result = new ArrayList<>();

        for (Complaint complaint : complaints) {
            if (complaint.getAssignedStaffId().equals(staff.getId())
                    && (complaint.getStatus() == ComplaintStatus.ASSIGNED
                    || complaint.getStatus() == ComplaintStatus.IN_PROGRESS)) {
                result.add(complaint);
            }
        }

        return result;
    }

    // MEMBER 2 - View completed complaints
    public ArrayList<Complaint> viewCompletedComplaints(Staff staff) {
        ArrayList<Complaint> result = new ArrayList<>();

        for (Complaint complaint : complaints) {
            if (complaint.getAssignedStaffId().equals(staff.getId())
                    && (complaint.getStatus() == ComplaintStatus.RESOLVED
                    || complaint.getStatus() == ComplaintStatus.CLOSED)) {
                result.add(complaint);
            }
        }

        return result;
    }

    // MEMBER 2 - Complaint lookup
    public Complaint findComplaintById(String complaintId)
            throws ComplaintNotFoundException {

        for (Complaint complaint : complaints) {
            if (complaint.getComplaintId().equals(complaintId)) {
                return complaint;
            }
        }

        throw new ComplaintNotFoundException("Complaint not found.");
    }

    // MEMBER 2 - Complaint list retrieval
    public ArrayList<Complaint> getAllComplaints() {
        return complaints;
    }
}