# Hostel Complaint Management System

Hostel Complaint Management System is a console-based mini-project that simulates a seamless complaint management process in hostels. It allows students to raise complaints, track their status, and close them once resolved. Administrators can assign complaints to appropriate staff members, while staff can update progress and mark complaints as resolved. The project is built using Java and demonstrates core Object-Oriented Programming (OOP) concepts.

## Features

### Student Features:
- Register with name, phone, username, password, and room number.
- Login with username and password (3 attempt limit).
- Raise complaints with various categories (Electrical, Plumbing, Wi-Fi, Cleaning, Furniture).
- View all complaints raised by the student.
- Track complaint status, assigned staff, and progress notes.
- Close resolved complaints (only the complaining student can close).

### Admin Features:
- View all complaints from all students in a formatted table.
- Assign pending complaints to appropriate staff with department validation.
- Search complaint by ID and view all details.
- Add new staff members with department assignment.
- View all staff with ID, name, and department.
- View complaint statistics by status (Pending, Assigned, In Progress, Resolved, Closed).

### Staff Features:
- View complaints assigned to the staff member.
- Update progress with notes (status changes to IN_PROGRESS).
- Mark complaints as resolved with resolution notes.
- View completed complaints (resolved and closed).

### User Authentication:
- Existing users can log in, while new users can register with a username and password.

### Navigation:
- Back option provided throughout the application and Logout option for easy navigation.

## Default Login Credentials

| Role | Username | Password | Department |
|------|----------|----------|------------|
| Admin | admin | admin123 | - |
| Staff | ravi | ravi123 | Electrical |
| Staff | kumar | kumar123 | Plumbing |
| Staff | sneha | sneha123 | Wi-Fi |
| Staff | anjali | anjali123 | Cleaning |
| Staff | rahul | rahul123 | Furniture |

## How to Run

### Clone the repository:
git clone https://github.com/yourusername/hostel-complaint-system.git
cd hostel-complaint-system/src
