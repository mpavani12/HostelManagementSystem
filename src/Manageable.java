package interfaces;

import exceptions.StaffNotFoundException;

// MEMBER 3 - Admin Management Contract
public interface Manageable {

    // MEMBER 3 - Complaint assignment contract
    void assignComplaint(String complaintId, String staffId)
            throws StaffNotFoundException;

    // MEMBER 3 - Statistics viewing contract
    void viewStatistics();
}