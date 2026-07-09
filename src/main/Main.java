package main;

import java.util.ArrayList;
import java.util.Scanner;

import exceptions.ComplaintNotFoundException;
import exceptions.DuplicateComplaintException;
import exceptions.DuplicateUserException;
import exceptions.InvalidComplaintStateException;
import exceptions.InvalidLoginException;
import exceptions.StaffNotFoundException;
import exceptions.UnauthorizedAccessException;
import model.Admin;
import model.Complaint;
import model.ComplaintStatus;
import model.Staff;
import model.Student;
import service.AdminService;
import service.ComplaintService;
import service.UserService;
import util.IDGenerator;
import util.ValidationUtil;
import util.ConsoleColors;
import util.ConsoleUI;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static ComplaintService complaintService = new ComplaintService();
    private static AdminService adminService =
            new AdminService(complaintService, userService);

    public static void main(String[] args) {
    	ConsoleUI.showStartupArt();
    	ConsoleUI.loadingEffect("Initializing Hostel Complaint System");
    	ConsoleUI.pause(500);
   

        while (true) {
            ConsoleUI.printMainBanner();

            System.out.println(
                    ConsoleColors.BRIGHT_GREEN +
                    "╔════════════════════════════════════════════╗"
            );

            System.out.println(
                    "║                MAIN MENU                   ║"
            );

            System.out.println(
                    "╠════════════════════════════════════════════╣"
            );

            System.out.println(
                    "║  1. Student Registration                   ║"
            );

            System.out.println(
                    "║  2. Student Login                          ║"
            );

            System.out.println(
                    "║  3. Admin Login                            ║" 
            );

            System.out.println(
                    "║  4. Staff Login                            ║"
            );

            System.out.println(
                    "║  5. Exit                                   ║"
            );

            System.out.println(
                    "╚════════════════════════════════════════════╝"
                    + ConsoleColors.RESET
            );

            System.out.print(
                    ConsoleColors.BRIGHT_YELLOW +
                    ConsoleColors.BOLD +
                    "➜ Enter choice: "
                    + ConsoleColors.RESET
            );

            int choice = readInt();

            switch (choice) {
                case 1:
                    registerStudent();
                    break;

                case 2:
                    studentLogin();
                    break;

                case 3:
                    adminLogin();
                    break;

                case 4:
                    staffLogin();
                    break;

                case 5:
                	ConsoleUI.printInfo("Saving Session");
                	ConsoleUI.loadingEffect("Closing Hostel Management System");

                	System.out.print(ConsoleColors.BRIGHT_GREEN);

                	ConsoleUI.typeWriter(
                	        "Thank you for using Hostel Complaint Management System.",
                	        25
                	);

                	System.out.println(ConsoleColors.RESET);
                    scanner.close();
                    System.exit(0);

                default:
                	ConsoleUI.printError("Invalid choice. Please try again.");
                	ConsoleUI.pause(800);
            }
        }
    }

    private static void registerStudent() {

        ConsoleUI.printHeader("STUDENT REGISTRATION");

        String name;
        while (true) {
            System.out.print(
                    ConsoleColors.CYAN +
                    "Enter Name: " +
                    ConsoleColors.RESET
            );

            name = scanner.nextLine();

            if (ValidationUtil.isValidName(name)) {
                break;
            }

            ConsoleUI.printError(
                    "Name must start with an alphabet, contain only letters, no spaces, and be minimum 5 characters."
            );
        }

        String phone;
        while (true) {
            System.out.print(
                    ConsoleColors.CYAN +
                    "Enter Phone: " +
                    ConsoleColors.RESET
            );

            phone = scanner.nextLine();

            if (!ValidationUtil.isValidPhone(phone)) {
                ConsoleUI.printError(
                        "Phone must be 10 digits and start with 6/7/8/9."
                );
                continue;
            }

            if (userService.isPhoneExists(phone)) {
                ConsoleUI.printError("Phone number already exists.");
                continue;
            }

            break;
        }

        String username;
        while (true) {
            System.out.print(
                    ConsoleColors.CYAN +
                    "Enter Username: " +
                    ConsoleColors.RESET
            );

            username = scanner.nextLine();

            if (!ValidationUtil.isValidUsername(username)) {
                ConsoleUI.printError(
                        "Username must start with a letter, contain only letters/numbers, and be minimum 6 characters."
                );
                continue;
            }

            if (userService.isUsernameExists(username)) {
                ConsoleUI.printError("Username already exists.");
                continue;
            }

            break;
        }

        String password = "";

        // PASSWORD SECTION
        int passwordAttempts = 0;
        boolean validPassword = false;

        while (passwordAttempts < 10) {

            System.out.print(
                    ConsoleColors.CYAN +
                    "Enter Password: " +
                    ConsoleColors.RESET
            );

            password = scanner.nextLine();

            String passwordError =
                    ValidationUtil.validatePassword(password);

            if (passwordError == null) {
                validPassword = true;
                break;
            }

            passwordAttempts++;

            ConsoleUI.printError(
                    passwordError +
                    " Attempts left: " + (10 - passwordAttempts)
            );
        }

        if (!validPassword) {
            ConsoleUI.printWarning(
                    "Too many invalid password attempts. Returning to main menu."
            );
            return;
        }

        // CONFIRM PASSWORD SECTION
        int confirmAttempts = 0;
        boolean matched = false;

        while (confirmAttempts < 3) {

            System.out.print(
                    ConsoleColors.CYAN +
                    "Confirm Password: " +
                    ConsoleColors.RESET
            );

            String confirmPassword = scanner.nextLine();

            if (password.equals(confirmPassword)) {
                matched = true;
                break;
            }

            confirmAttempts++;

            ConsoleUI.printError(
                    "Passwords do not match. Attempts left: " + (3 - confirmAttempts)
            );
        }

        if (!matched) {
            ConsoleUI.printWarning(
                    "Too many failed confirm attempts. Returning to main menu."
            );
            return;
        }

        String room;
        while (true) {
            System.out.print(
                    ConsoleColors.CYAN +
                    "Enter Room Number: " +
                    ConsoleColors.RESET
            );

            room = scanner.nextLine();

            if (ValidationUtil.isValidRoomNumber(room)) {
                break;
            }

            ConsoleUI.printError(
                    "Room number must contain digits only."
            );
        }

        try {
            userService.registerStudent(
                    name,
                    phone,
                    username,
                    password,
                    room
            );

            ConsoleUI.printSuccess(
                    "Student registered successfully."
            );

        } catch (DuplicateUserException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    private static void studentLogin() {

        int attempts = 0;

        while (attempts < 3) {

            ConsoleUI.printHeader("STUDENT LOGIN PORTAL");

            String username;
            while (true) {
                System.out.print(
                        ConsoleColors.BRIGHT_CYAN +
                        ConsoleColors.BOLD +
                        "➜ Enter Username: "
                        + ConsoleColors.RESET
                );

                username = scanner.nextLine();

                if (!ValidationUtil.isEmpty(username)) {
                    break;
                }

                ConsoleUI.printError("Username cannot be empty.");
            }

            String password;
            while (true) {
                System.out.print(
                        ConsoleColors.BRIGHT_CYAN +
                        ConsoleColors.BOLD +
                        "➜ Enter Password: "
                        + ConsoleColors.RESET
                );

                password = scanner.nextLine();

                if (!ValidationUtil.isEmpty(password)) {
                    break;
                }

                ConsoleUI.printError("Password cannot be empty.");
            }

            try {

                ConsoleUI.loadingEffect("Authenticating Student");

                Student student =
                        userService.studentLogin(username, password);

                ConsoleUI.printSuccess("Student login successful.");

                ConsoleUI.pause(600);

                studentMenu(student);
                return;

            } catch (InvalidLoginException e) {

                attempts++;

                ConsoleUI.printError(
                        "Invalid student credentials. Attempts left: " + (3 - attempts)
                );

                if (attempts >= 3) {
                    ConsoleUI.printError(
                            "Maximum login attempts reached. Returning to main menu."
                    );
                    ConsoleUI.pause(1000);
                    return;
                }

                System.out.println(
                        ConsoleColors.BRIGHT_YELLOW +
                        "╔════════════════════════════════════════════════════╗"
                );

                System.out.println(
                        "║  ① Retry Login                                     ║"
                );

                System.out.println(
                        "║  ② Back To Main Menu                               ║"
                );

                System.out.println(
                        "╚════════════════════════════════════════════════════╝"
                        + ConsoleColors.RESET
                );

                System.out.print(
                        ConsoleColors.BRIGHT_CYAN +
                        "➜ Enter choice: "
                        + ConsoleColors.RESET
                );

                int choice = readInt();

                if (choice == 2) {
                    return;
                }
            }
        }
    }

    private static void studentMenu(Student student){
        while (true) {
            student.showMenu();
            System.out.print("Enter choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    raiseComplaint(student);
                    break;

                case 2:
                    viewMyComplaints(student);
                    break;

                case 3:
                    trackComplaint();
                    break;

                case 4:
                    closeComplaint(student);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    
    private static void raiseComplaint(Student student) {

        try {

            ConsoleUI.printHeader("RAISE NEW COMPLAINT");

            int categoryChoice;

            while (true) {

                System.out.println();

                System.out.println(
                        ConsoleColors.BRIGHT_GREEN +
                        "╔══════════════════════════════════════╗"
                );

                System.out.println(
                        "║         COMPLAINT CATEGORIES         ║"
                );

                System.out.println(
                        "╠══════════════════════════════════════╣"
                );

                System.out.println(
                        ConsoleColors.BRIGHT_YELLOW +
                        "║  1. ⚡ Electrical                     ║"  
                );

                System.out.println(
                        "║  2. 🔧 Plumbing                      ║"
                );

                System.out.println(
                        "║  3. 📶 Wi-Fi                         ║"
                );

                System.out.println(
                        "║  4. 🧹 Cleaning                      ║"
                );

                System.out.println(
                        "║  5. 🪑 Furniture                     ║"
                );

                System.out.println(
                        ConsoleColors.BRIGHT_GREEN +
                        "╚══════════════════════════════════════╝"
                        + ConsoleColors.RESET
                );

                System.out.print(
                        ConsoleColors.BRIGHT_CYAN +
                        ConsoleColors.BOLD +
                        "➜ Select category: "
                        + ConsoleColors.RESET
                );

                categoryChoice = readInt();

                if (ValidationUtil.isValidComplaintCategory(categoryChoice)) {
                    break;
                }

                ConsoleUI.printError("Invalid category selection. Try again.");
            }

            String category =
                    ValidationUtil.getCategoryName(categoryChoice);

            String title;
            while (true) {

                System.out.print(
                        ConsoleColors.CYAN +
                        "Enter Complaint Title: "
                        + ConsoleColors.RESET
                );

                title = scanner.nextLine();

                if (!ValidationUtil.isEmpty(title)) {
                    break;
                }

                ConsoleUI.printError("Complaint title cannot be empty.");
            }

            String description;
            while (true) {

                System.out.print(
                        ConsoleColors.CYAN +
                        "Enter Description: "
                        + ConsoleColors.RESET
                );

                description = scanner.nextLine();

                if (!ValidationUtil.isEmpty(description)) {
                    break;
                }

                ConsoleUI.printError("Description cannot be empty.");
            }

            complaintService.raiseComplaint(
                    student,
                    category,
                    title,
                    description,
                    IDGenerator.generateComplaintId()
            );

            ConsoleUI.printSuccess("Complaint raised successfully.");

        } catch (DuplicateComplaintException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    private static void viewMyComplaints(Student student) {

        ArrayList<Complaint> complaints =
                complaintService.viewStudentComplaints(student);

        ConsoleUI.printHeader("MY COMPLAINTS");

        if (complaints.isEmpty()) {
            ConsoleUI.printWarning("No complaints found.");
            return;
        }

        System.out.println(
                ConsoleColors.BRIGHT_YELLOW +
                "+------------+----------------------+--------------+"
        );

        System.out.printf(
                "| %-10s | %-20s | %-12s |\n",
                "ID",
                "TITLE",
                "STATUS"
        );

        System.out.println(
                "+------------+----------------------+--------------+"
        );

        for (Complaint complaint : complaints) {
            System.out.printf(
                    "| %-10s | %-20s | %-12s |\n",
                    complaint.getComplaintId(),
                    complaint.getTitle(),
                    complaint.getStatus()
            );
        }

        System.out.println(
                "+------------+----------------------+--------------+"
                + ConsoleColors.RESET
        );
    }

    private static void trackComplaint() {

        ConsoleUI.printHeader("TRACK COMPLAINT");

        System.out.print(
                ConsoleColors.CYAN +
                "Enter Complaint ID: "
                + ConsoleColors.RESET
        );

        String complaintId = scanner.nextLine();

        complaintService.trackStatus(complaintId);
    }
    private static void closeComplaint(Student student) {
        try {

            ConsoleUI.printHeader("CLOSE COMPLAINT");

            System.out.print(
                    ConsoleColors.CYAN +
                    "Enter Complaint ID: "
                    + ConsoleColors.RESET
            );

            String complaintId = scanner.nextLine();

            complaintService.closeComplaint(complaintId, student);

            ConsoleUI.printSuccess("Complaint closed successfully.");

        } catch (ComplaintNotFoundException |
                 InvalidComplaintStateException e) {

            ConsoleUI.printError(e.getMessage());
        }
    }
    private static void adminLogin() {

        int attempts = 0;

        while (attempts < 3) {

            ConsoleUI.printHeader("ADMIN AUTHENTICATION");

            String username;
            while (true) {
                System.out.print(
                        ConsoleColors.BRIGHT_CYAN +
                        ConsoleColors.BOLD +
                        "➜ Enter Admin Username: "
                        + ConsoleColors.RESET
                );

                username = scanner.nextLine();

                if (!ValidationUtil.isEmpty(username)) {
                    break;
                }

                ConsoleUI.printError("Username cannot be empty.");
            }

            String password;
            while (true) {
                System.out.print(
                        ConsoleColors.BRIGHT_CYAN +
                        ConsoleColors.BOLD +
                        "➜ Enter Admin Password: "
                        + ConsoleColors.RESET
                );

                password = scanner.nextLine();

                if (!ValidationUtil.isEmpty(password)) {
                    break;
                }

                ConsoleUI.printError("Password cannot be empty.");
            }

            try {

                ConsoleUI.loadingEffect("Verifying Admin Access");

                Admin admin =
                        userService.adminLogin(username, password);

                ConsoleUI.printSuccess("Admin login successful.");

                ConsoleUI.pause(600);

                adminMenu(admin);
                return;

            } catch (InvalidLoginException e) {

                attempts++;

                ConsoleUI.printError(
                        "Invalid admin credentials. Attempts left: " + (3 - attempts)
                );

                if (attempts >= 3) {
                    ConsoleUI.printError(
                            "Maximum login attempts reached. Returning to main menu."
                    );
                    ConsoleUI.pause(1000);
                    return;
                }

                System.out.println(
                        ConsoleColors.BRIGHT_YELLOW +
                        "╔════════════════════════════════════════════════════╗"
                );

                System.out.println(
                        "║  ① Retry Login                                    ║"
                );

                System.out.println(
                        "║  ② Back To Main Menu                              ║"
                );

                System.out.println(
                        "╚════════════════════════════════════════════════════╝"
                        + ConsoleColors.RESET
                );

                System.out.print(
                        ConsoleColors.BRIGHT_CYAN +
                        "➜ Enter choice: "
                        + ConsoleColors.RESET
                );

                int choice = readInt();

                if (choice == 2) {
                    return;
                }
            }
        }
    }

    private static void adminMenu(Admin admin) {
        while (true) {
            admin.showMenu();
            System.out.print("Enter choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    adminService.viewAllComplaints();
                    break;

                case 2:
                    assignComplaint();
                    break;

                case 3:
                    searchComplaint();
                    break;

                case 4:
                    addStaff();
                    break;

                case 5:
                    viewStaff();
                    break;

                case 6:
                    adminService.viewStatistics();
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void assignComplaint() {

        ConsoleUI.printHeader("ASSIGN COMPLAINT");

        System.out.print(
                ConsoleColors.BRIGHT_CYAN +
                "➜ Enter Complaint ID: " +
                ConsoleColors.RESET
        );

        String complaintId = scanner.nextLine();

        try {

            // Validate complaint first
            Complaint complaint =
                    complaintService.findComplaintById(complaintId);

            // Optional: only pending complaints should be assignable
            if (complaint.getStatus() != ComplaintStatus.PENDING) {
                ConsoleUI.printWarning(
                        "Only pending complaints can be assigned."
                );
                return;
            }

            // Show staff only if complaint exists
            viewStaff();

            System.out.print(
                    ConsoleColors.BRIGHT_CYAN +
                    "➜ Enter Staff ID: " +
                    ConsoleColors.RESET
            );

            String staffId = scanner.nextLine();

            adminService.assignComplaint(complaintId, staffId);

        } catch (ComplaintNotFoundException e) {
            ConsoleUI.printError(e.getMessage());

        } catch (StaffNotFoundException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    private static void searchComplaint() {
        System.out.print("Enter Complaint ID: ");
        String complaintId = scanner.nextLine();

        adminService.searchComplaint(complaintId);
    }

    private static void addStaff() {

        ConsoleUI.printHeader("ADD NEW STAFF");

        String name;
        while (true) {
            System.out.print(
                    ConsoleColors.CYAN +
                    "Enter Name: " +
                    ConsoleColors.RESET
            );

            name = scanner.nextLine();

            if (ValidationUtil.isValidName(name)) {
                break;
            }

            ConsoleUI.printError(
                    "Name must start with an alphabet, contain only letters, no spaces, and be minimum 5 characters."
            );
        }

        String phone;
        while (true) {
            System.out.print(
                    ConsoleColors.CYAN +
                    "Enter Phone: " +
                    ConsoleColors.RESET
            );

            phone = scanner.nextLine();

            if (!ValidationUtil.isValidPhone(phone)) {
                ConsoleUI.printError(
                        "Phone must be 10 digits and start with 6/7/8/9."
                );
                continue;
            }

            if (userService.isPhoneExists(phone)) {
                ConsoleUI.printError("Phone number already exists.");
                continue;
            }

            break;
        }

        String username;
        while (true) {
            System.out.print(
                    ConsoleColors.CYAN +
                    "Enter Username: " +
                    ConsoleColors.RESET
            );

            username = scanner.nextLine();

            if (!ValidationUtil.isValidUsername(username)) {
                ConsoleUI.printError(
                        "Username must start with a letter, contain only letters/numbers, and be minimum 6 characters."
                );
                continue;
            }

            if (userService.isUsernameExists(username)) {
                ConsoleUI.printError("Username already exists.");
                continue;
            }

            break;
        }

        String password = "";

        // PASSWORD VALIDATION
        int passwordAttempts = 0;
        boolean validPassword = false;

        while (passwordAttempts < 10) {

            System.out.print(
                    ConsoleColors.CYAN +
                    "Enter Password: " +
                    ConsoleColors.RESET
            );

            password = scanner.nextLine();

            String passwordError =
                    ValidationUtil.validatePassword(password);

            if (passwordError == null) {
                validPassword = true;
                break;
            }

            passwordAttempts++;

            ConsoleUI.printError(
                    passwordError +
                    " Attempts left: " + (10 - passwordAttempts)
            );
        }

        if (!validPassword) {
            ConsoleUI.printWarning(
                    "Too many invalid password attempts. Returning to admin menu."
            );
            return;
        }

        // CONFIRM PASSWORD
        int confirmAttempts = 0;
        boolean matched = false;

        while (confirmAttempts < 3) {

            System.out.print(
                    ConsoleColors.CYAN +
                    "Confirm Password: " +
                    ConsoleColors.RESET
            );

            String confirmPassword = scanner.nextLine();

            if (password.equals(confirmPassword)) {
                matched = true;
                break;
            }

            confirmAttempts++;

            ConsoleUI.printError(
                    "Passwords do not match. Attempts left: " + (3 - confirmAttempts)
            );
        }

        if (!matched) {
            ConsoleUI.printWarning(
                    "Too many failed confirm attempts. Returning to admin menu."
            );
            return;
        }

        String department = "";

        while (true) {

            System.out.println();

            System.out.println(
                    ConsoleColors.BRIGHT_GREEN +
                    "╔══════════════════════════════════════╗"
            );

            System.out.println(
                    "║          STAFF DEPARTMENTS           ║"
            );

            System.out.println(
                    "╠══════════════════════════════════════╣"
            );

            System.out.println(
                    ConsoleColors.BRIGHT_GREEN +
                    "║  1. ⚡ Electrical                     ║"
            );

            System.out.println(
                    "║  2. 🔧 Plumbing                      ║"
            );

            System.out.println(
                    "║  3. 📶 Wi-Fi                         ║"
            );

            System.out.println(
                    "║  4. 🧹 Cleaning                      ║"
            );

            System.out.println(
                    "║  5. 🪑 Furniture                     ║"
            );

            System.out.println(
                    ConsoleColors.BRIGHT_GREEN +
                    "╚══════════════════════════════════════╝"
                    + ConsoleColors.RESET
            );

            System.out.print(
                    ConsoleColors.BRIGHT_CYAN +
                    ConsoleColors.BOLD +
                    "➜ Enter choice: "
                    + ConsoleColors.RESET
            );

            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        department = "Electrical";
                        break;
                    case 2:
                        department = "Plumbing";
                        break;
                    case 3:
                        department = "Wi-Fi";
                        break;
                    case 4:
                        department = "Cleaning";
                        break;
                    case 5:
                        department = "Furniture";
                        break;
                    default:
                        ConsoleUI.printError("Invalid department choice.");
                        continue;
                }

                break;

            } catch (NumberFormatException e) {
                ConsoleUI.printError("Enter a valid numeric choice.");
            }
        }

        try {
            userService.addStaff(
                    name,
                    phone,
                    username,
                    password,
                    department
            );

            ConsoleUI.printSuccess("Staff added successfully.");

        } catch (DuplicateUserException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    private static void viewStaff() {

        ArrayList<Staff> staffList = userService.getAllStaff();

        if (staffList.isEmpty()) {
            ConsoleUI.printWarning("No staff members found.");
            return;
        }

        ConsoleUI.printHeader("STAFF DIRECTORY");

        System.out.println(
                ConsoleColors.BRIGHT_YELLOW +
                "╔════════════╦══════════════════════╦════════════════════╗"
        );

        System.out.println(
                "║ STAFF ID   ║ NAME                 ║ DEPARTMENT         ║"
        );

        System.out.println(
                "╠════════════╬══════════════════════╬════════════════════╣"
        );

        for (Staff staff : staffList) {

            System.out.printf(
                    "║ %-10s ║ %-20s ║ %-18s ║%n",
                    staff.getId(),
                    staff.getName(),
                    staff.getDepartment()
            );
        }

        System.out.println(
                "╚════════════╩══════════════════════╩════════════════════╝"
                + ConsoleColors.RESET
        );
    }

    private static void staffLogin() {

        int attempts = 0;

        while (attempts < 3) {

            ConsoleUI.printHeader("STAFF LOGIN PORTAL");

            String username;
            while (true) {
                System.out.print(
                        ConsoleColors.BRIGHT_CYAN +
                        ConsoleColors.BOLD +
                        "➜ Enter Staff Username: "
                        + ConsoleColors.RESET
                );

                username = scanner.nextLine();

                if (!ValidationUtil.isEmpty(username)) {
                    break;
                }

                ConsoleUI.printError("Username cannot be empty.");
            }

            String password;
            while (true) {
                System.out.print(
                        ConsoleColors.BRIGHT_CYAN +
                        ConsoleColors.BOLD +
                        "➜ Enter Staff Password: "
                        + ConsoleColors.RESET
                );

                password = scanner.nextLine();

                if (!ValidationUtil.isEmpty(password)) {
                    break;
                }

                ConsoleUI.printError("Password cannot be empty.");
            }

            try {

                ConsoleUI.loadingEffect("Authenticating Staff");

                Staff staff =
                        userService.staffLogin(username, password);

                ConsoleUI.printSuccess("Staff login successful.");

                ConsoleUI.pause(600);

                staffMenu(staff);
                return;

            } catch (InvalidLoginException e) {

                attempts++;

                ConsoleUI.printError(
                        "Invalid staff credentials. Attempts left: " + (3 - attempts)
                );

                if (attempts >= 3) {
                    ConsoleUI.printError(
                            "Maximum login attempts reached. Returning to main menu."
                    );
                    ConsoleUI.pause(1000);
                    return;
                }

                System.out.println(
                        ConsoleColors.BRIGHT_YELLOW +
                        "╔════════════════════════════════════════════════════╗"
                );

                System.out.println(
                        "║  ① Retry Login                                    ║"
                );

                System.out.println(
                        "║  ② Back To Main Menu                              ║"
                );

                System.out.println(
                        "╚════════════════════════════════════════════════════╝"
                        + ConsoleColors.RESET
                );

                System.out.print(
                        ConsoleColors.BRIGHT_CYAN +
                        "➜ Enter choice: "
                        + ConsoleColors.RESET
                );

                int choice = readInt();

                if (choice == 2) {
                    return;
                }
            }
        }
    }

    private static void staffMenu(Staff staff) {
        while (true) {
            staff.showMenu();
            System.out.print("Enter choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    viewAssignedComplaints(staff);
                    break;

                case 2:
                    updateProgress(staff);
                    break;

                case 3:
                    markResolved(staff);
                    break;

                case 4:
                    viewCompletedComplaints(staff);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewAssignedComplaints(Staff staff) {

        ArrayList<Complaint> complaints =
                complaintService.viewAssignedComplaints(staff);

        if (complaints.isEmpty()) {
            ConsoleUI.printWarning("No assigned complaints.");
            return;
        }

        ConsoleUI.printHeader("ASSIGNED COMPLAINTS");

        System.out.println(
                ConsoleColors.BRIGHT_YELLOW +
                "╔════════════╦══════════════════════╦══════════╦════════════════════╦══════════════╗"
        );

        System.out.println(
                "║ COMPLAINT  ║ TITLE                ║ ROOM NO  ║ STUDENT NAME       ║ STATUS       ║"
        );

        System.out.println(
                "╠════════════╬══════════════════════╬══════════╬════════════════════╬══════════════╣"
        );

        for (Complaint complaint : complaints) {

            System.out.printf(
                    "║ %-10s ║ %-20s ║ %-8s ║ %-18s ║ %-12s ║%n",
                    complaint.getComplaintId(),
                    complaint.getTitle(),
                    complaint.getRoomNumber(),
                    complaint.getStudentName(),
                    complaint.getStatus()
            );
        }

        System.out.println(
                "╚════════════╩══════════════════════╩══════════╩════════════════════╩══════════════╝"
                + ConsoleColors.RESET
        );
    }

    private static void updateProgress(Staff staff) {

        try {

            ConsoleUI.printHeader("UPDATE COMPLAINT PROGRESS");

            System.out.println(
                    ConsoleColors.BRIGHT_YELLOW +
                    "╔════════════════════════════════════════════════════╗"
            );

            System.out.println(
                    "║             STAFF PROGRESS UPDATE PANEL           ║"
            );

            System.out.println(
                    "╚════════════════════════════════════════════════════╝"
                    + ConsoleColors.RESET
            );

            System.out.print(
                    ConsoleColors.BRIGHT_YELLOW +
                    ConsoleColors.BOLD +
                    "➜ Enter Complaint ID: "
                    + ConsoleColors.RESET
            );

            String complaintId = scanner.nextLine();

            System.out.print(
                    ConsoleColors.BRIGHT_YELLOW +
                    ConsoleColors.BOLD +
                    "➜ Enter Progress Note: "
                    + ConsoleColors.RESET
            );

            String note = scanner.nextLine();

            complaintService.updateProgress(
                    complaintId,
                    staff,
                    note
            );

            ConsoleUI.printSuccess("Progress updated successfully.");

        } catch (ComplaintNotFoundException | UnauthorizedAccessException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    private static void markResolved(Staff staff) {

        try {

            ConsoleUI.printHeader("MARK COMPLAINT RESOLVED");

            System.out.println(
                    ConsoleColors.BRIGHT_YELLOW +
                    "╔════════════════════════════════════════════════════╗"
            );

            System.out.println(
                    "║              COMPLAINT RESOLUTION PANEL           ║"
            );

            System.out.println(
                    "╚════════════════════════════════════════════════════╝"
                    + ConsoleColors.RESET
            );

            System.out.print(
                    ConsoleColors.BRIGHT_YELLOW +
                    ConsoleColors.BOLD +
                    "➜ Enter Complaint ID: "
                    + ConsoleColors.RESET
            );

            String complaintId = scanner.nextLine();

            System.out.print(
                    ConsoleColors.BRIGHT_YELLOW +
                    ConsoleColors.BOLD +
                    "➜ Enter Resolution Note: "
                    + ConsoleColors.RESET
            );

            String resolutionNote = scanner.nextLine();

            complaintService.markResolved(
                    complaintId,
                    staff,
                    resolutionNote
            );

            ConsoleUI.printSuccess("Complaint marked resolved successfully.");

        } catch (ComplaintNotFoundException | UnauthorizedAccessException e) {
            ConsoleUI.printError(e.getMessage());
        }
    }

    private static void viewCompletedComplaints(Staff staff) {

        ArrayList<Complaint> complaints =
                complaintService.viewCompletedComplaints(staff);

        if (complaints.isEmpty()) {
            ConsoleUI.printWarning("No completed complaints.");
            return;
        }

        ConsoleUI.printHeader("COMPLETED COMPLAINTS");

        System.out.println(
                ConsoleColors.BRIGHT_YELLOW +
                "╔════════════╦══════════════════════════════╦══════════════╗"
        );

        System.out.println(
                "║ COMPLAINT  ║ TITLE                        ║ STATUS       ║"
        );

        System.out.println(
                "╠════════════╬══════════════════════════════╬══════════════╣"
        );

        for (Complaint complaint : complaints) {

            System.out.printf(
                    "║ %-10s ║ %-28s ║ %-12s ║%n",
                    complaint.getComplaintId(),
                    complaint.getTitle(),
                    complaint.getStatus()
            );
        }

        System.out.println(
                "╚════════════╩══════════════════════════════╩══════════════╝"
                + ConsoleColors.RESET
        );
    }

    private static int readInt() {
        try {
            int value = Integer.parseInt(scanner.nextLine());
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}