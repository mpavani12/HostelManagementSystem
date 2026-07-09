package interfaces;

import exceptions.ComplaintNotFoundException;
import exceptions.UnauthorizedAccessException;
import model.Staff;

// MEMBER 4 - Complaint Tracking Contract
public interface Trackable {

    // MEMBER 4 - Progress update contract
	void updateProgress(String complaintId, Staff currentStaff, String note)
	        throws ComplaintNotFoundException,
	        UnauthorizedAccessException;

    // MEMBER 4 - Complaint tracking contract
    void trackStatus(String complaintId);
}