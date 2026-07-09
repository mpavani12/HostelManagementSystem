package service;

import java.util.ArrayList;

import exceptions.ComplaintNotFoundException;
import exceptions.StaffNotFoundException;
import interfaces.Manageable;
import model.Complaint;
import model.ComplaintStatus;
import model.Staff;
import util.ConsoleColors;
import util.ConsoleUI;

// MEMBER 3 - Admin Operations
public class AdminService implements Manageable {

    private ComplaintService complaintService;
    private UserService userService;

    // MEMBER 3 - Service dependency initialization
    public AdminService(ComplaintService complaintService, UserService userService) {
        this.complaintService = complaintService;
        this.userService = userService;
    }

    // MEMBER 3 - View all complaints
    public void viewAllComplaints() {

        ArrayList<Complaint> complaints = complaintService.getAllComplaints();

        ConsoleUI.printHeader("ALL COMPLAINTS");

        if (complaints.isEmpty()) {
            ConsoleUI.printWarning("No complaints available.");
            return;
        }

        System.out.println(
                ConsoleColors.BRIGHT_YELLOW +
                "╔════════════╦══════════════════╦══════════╦══════════════╦════════════════╗"
        );

        System.out.printf(
                "║ %-10s ║ %-16s ║ %-8s ║ %-12s ║ %-14s ║\n",
                "ID",
                "STUDENT",
                "ROOM",
                "CATEGORY",
                "STATUS"
        );

        System.out.println(
                "╠════════════╬══════════════════╬══════════╬══════════════╬════════════════╣"
        );

        for (Complaint complaint : complaints) {

            System.out.printf(
                    "║ %-10s ║ %-16s ║ %-8s ║ %-12s ║ %-14s ║\n",
                    complaint.getComplaintId(),
                    complaint.getStudentName(),
                    complaint.getRoomNumber(),
                    complaint.getCategory(),
                    complaint.getStatus()
            );
        }

        System.out.println(
                "╚════════════╩══════════════════╩══════════╩══════════════╩════════════════╝"
                + ConsoleColors.RESET
        );
    }

    // MEMBER 3 - Complaint assignment
    @Override
    public void assignComplaint(String complaintId, String staffId)
            throws StaffNotFoundException {

        try {

            Complaint complaint =
                    complaintService.findComplaintById(complaintId);

            Staff staff =
                    userService.findStaffById(staffId);

            // STAFF VALIDATION
            if (staff == null) {
                throw new StaffNotFoundException("Staff ID not found.");
            }

            // STATUS VALIDATION
            if (complaint.getStatus() != ComplaintStatus.PENDING) {
                ConsoleUI.printWarning(
                        "Only pending complaints can be assigned."
                );
                return;
            }

            // DEPARTMENT VALIDATION
            if (!complaint.getCategory().equalsIgnoreCase(staff.getDepartment())) {
                ConsoleUI.printError(
                        "Department mismatch detected. Assign matching department staff only."
                );
                return;
            }

            complaint.setAssignedStaffId(staff.getId());
            complaint.setAssignedStaffName(staff.getName());
            complaint.setStatus(ComplaintStatus.ASSIGNED);

            ConsoleUI.loadingEffect("Assigning Complaint");
            ConsoleUI.printSuccess("Complaint assigned successfully.");

        } catch (ComplaintNotFoundException e) {
            ConsoleUI.printError(e.getMessage());

        } catch (StaffNotFoundException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    // MEMBER 3 - Complaint search
    public void searchComplaint(String complaintId) {

        try {

            Complaint complaint =
                    complaintService.findComplaintById(complaintId);

            ConsoleUI.printHeader("COMPLAINT DETAILS");

            System.out.println(
                    ConsoleColors.BRIGHT_CYAN +
                    "╔══════════════════════════════════════════════════════════════╗"
            );

            System.out.printf("║ Complaint ID     : %-40s ║\n", complaint.getComplaintId());
            System.out.printf("║ Student Name     : %-40s ║\n", complaint.getStudentName());
            System.out.printf("║ Room Number      : %-40s ║\n", complaint.getRoomNumber());
            System.out.printf("║ Category         : %-40s ║\n", complaint.getCategory());
            System.out.printf("║ Title            : %-40s ║\n", complaint.getTitle());
            System.out.printf("║ Status           : %-40s ║\n", complaint.getStatus());
            System.out.printf("║ Assigned Staff   : %-40s ║\n", complaint.getAssignedStaffName());
            System.out.printf("║ Progress Note    : %-40s ║\n", complaint.getProgressNote());

            System.out.println(
                    "╚══════════════════════════════════════════════════════════════╝"
                    + ConsoleColors.RESET
            );

        } catch (ComplaintNotFoundException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    // MEMBER 3 - Complaint statistics
    @Override
    public void viewStatistics() {

        ArrayList<Complaint> complaints =
                complaintService.getAllComplaints();

        int pending = 0;
        int assigned = 0;
        int inProgress = 0;
        int resolved = 0;
        int closed = 0;

        for (Complaint complaint : complaints) {

            switch (complaint.getStatus()) {

                case PENDING:
                    pending++;
                    break;

                case ASSIGNED:
                    assigned++;
                    break;

                case IN_PROGRESS:
                    inProgress++;
                    break;

                case RESOLVED:
                    resolved++;
                    break;

                case CLOSED:
                    closed++;
                    break;
            }
        }

        ConsoleUI.printHeader("SYSTEM STATISTICS");

        System.out.println(
                ConsoleColors.BRIGHT_YELLOW +
                "╔══════════════════════╦════════════╗"
        );

        System.out.printf(
                "║ %-20s ║ %-10s ║\n",
                "STATUS",
                "COUNT"
        );

        System.out.println(
                "╠══════════════════════╬════════════╣"
        );

        System.out.printf("║ %-20s ║ %-10d ║\n", "Pending", pending);
        System.out.printf("║ %-20s ║ %-10d ║\n", "Assigned", assigned);
        System.out.printf("║ %-20s ║ %-10d ║\n", "In Progress", inProgress);
        System.out.printf("║ %-20s ║ %-10d ║\n", "Resolved", resolved);
        System.out.printf("║ %-20s ║ %-10d ║\n", "Closed", closed);

        System.out.println(
                "╚══════════════════════╩════════════╝"
                + ConsoleColors.RESET
        );
    }
}