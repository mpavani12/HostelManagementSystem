package service;

import java.util.ArrayList;

import exceptions.DuplicateUserException;
import exceptions.InvalidLoginException;
import model.Admin;
import model.Staff;
import model.Student;
import util.IDGenerator;

// MEMBER 1 - Authentication & User Management
public class UserService {

    private ArrayList<Student> students;
    private ArrayList<Staff> staffList;
    private Admin admin;

    public UserService() {
        students = new ArrayList<>();
        staffList = new ArrayList<>();

        admin = new Admin(
                "AD001",
                "Admin",
                "9999988888",
                "admin",
                "admin123"
        );

        preloadStaff();
    }

    // MEMBER 1 - Default staff initialization
    private void preloadStaff() {

        staffList.add(new Staff(
                IDGenerator.generateStaffId(),
                "Ravi",
                "9876543210",
                "ravi",
                "ravi123",
                "Electrical"
        ));

        staffList.add(new Staff(
                IDGenerator.generateStaffId(),
                "Kumar",
                "9876543211",
                "kumar",
                "kumar123",
                "Plumbing"
        ));

        staffList.add(new Staff(
                IDGenerator.generateStaffId(),
                "Sneha",
                "9876543212",
                "sneha",
                "sneha123",
                "Wi-Fi"
        ));

        staffList.add(new Staff(
                IDGenerator.generateStaffId(),
                "Anjali",
                "9876543213",
                "anjali",
                "anjali123",
                "Cleaning"
        ));

        staffList.add(new Staff(
                IDGenerator.generateStaffId(),
                "Rahul",
                "9876543214",
                "rahul",
                "rahul123",
                "Furniture"
        ));
    }

    // MEMBER 1 - Student registration
    public void registerStudent(String name, String phone,
                                String username, String password,
                                String roomNumber)
            throws DuplicateUserException {

        if (isUsernameExists(username)) {
            throw new DuplicateUserException("Username already exists.");
        }

        if (isPhoneExists(phone)) {
            throw new DuplicateUserException("Phone number already exists.");
        }

        Student student = new Student(
                IDGenerator.generateStudentId(),
                name,
                phone,
                username,
                password,
                roomNumber
        );

        students.add(student);
    }

    // MEMBER 1 - Student authentication
    public Student studentLogin(String username, String password)
            throws InvalidLoginException {

        for (Student student : students) {

            if (student.getUsername().equals(username)
                    && student.getPassword().equals(password)) {
                return student;
            }
        }

        throw new InvalidLoginException("Invalid student credentials.");
    }

    // MEMBER 1 - Admin authentication
    public Admin adminLogin(String username, String password)
            throws InvalidLoginException {

        if (admin.getUsername().equals(username)
                && admin.getPassword().equals(password)) {
            return admin;
        }

        throw new InvalidLoginException("Invalid admin credentials.");
    }

    // MEMBER 1 - Staff authentication
    public Staff staffLogin(String username, String password)
            throws InvalidLoginException {

        for (Staff staff : staffList) {

            if (staff.getUsername().equals(username)
                    && staff.getPassword().equals(password)) {
                return staff;
            }
        }

        throw new InvalidLoginException("Invalid staff credentials.");
    }

    // MEMBER 1 - Admin adds staff users
    public void addStaff(String name, String phone,
                         String username, String password,
                         String department)
            throws DuplicateUserException {

        if (isUsernameExists(username)) {
            throw new DuplicateUserException("Username already exists.");
        }

        if (isPhoneExists(phone)) {
            throw new DuplicateUserException("Phone number already exists.");
        }

        Staff staff = new Staff(
                IDGenerator.generateStaffId(),
                name,
                phone,
                username,
                password,
                department
        );

        staffList.add(staff);
    }

    // MEMBER 1 - Staff retrieval
    public ArrayList<Staff> getAllStaff() {
        return staffList;
    }

    // MEMBER 1 - Student retrieval
    public ArrayList<Student> getAllStudents() {
        return students;
    }

    // MEMBER 1 - Staff lookup
    public Staff findStaffById(String staffId) {

        for (Staff staff : staffList) {

            if (staff.getId().equals(staffId)) {
                return staff;
            }
        }

        return null;
    }

    // MEMBER 1 - Duplicate username validation
    public boolean isUsernameExists(String username) {

        if (admin.getUsername().equalsIgnoreCase(username)) {
            return true;
        }

        for (Student student : students) {
            if (student.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }

        for (Staff staff : staffList) {
            if (staff.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }

        return false;
    }

    // MEMBER 1 - Duplicate phone validation
    public boolean isPhoneExists(String phone) {

        if (admin.getPhone().equals(phone)) {
            return true;
        }

        for (Student student : students) {

            if (student.getPhone().equals(phone)) {
                return true;
            }
        }

        for (Staff staff : staffList) {

            if (staff.getPhone().equals(phone)) {
                return true;
            }
        }

        return false;
    }
}