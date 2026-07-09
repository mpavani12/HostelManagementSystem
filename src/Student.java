package model;

import util.ConsoleColors;
import util.ConsoleUI;

// MEMBER 2 - Student Complaint Lifecycle
public class Student extends Person {

    private String roomNumber;

    // MEMBER 2 - Student object creation
    public Student(String id, String name, String phone,
                   String username, String password,
                   String roomNumber) {
        super(id, name, phone, username, password);
        this.roomNumber = roomNumber;
    }

    // MEMBER 2 - Room number access
    public String getRoomNumber() {
        return roomNumber;
    }

    // MEMBER 2 - Room number update
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    // MEMBER 2 - Student menu display
    @Override
    public void showMenu() {

        ConsoleUI.printHeader("STUDENT DASHBOARD");

        System.out.println(
                ConsoleColors.BRIGHT_GREEN +
                "║  ① Raise Complaint                                                ║\n" +
                "║  ② View My Complaints                                             ║\n" +
                "║  ③ Track Complaint                                                ║\n" +
                "║  ④ Close Complaint                                                ║\n" +
                "║  ⑤ Logout                                                         ║"
                + ConsoleColors.RESET
        );

        System.out.println(
                ConsoleColors.BRIGHT_GREEN +
                "╚════════════════════════════════════════════════════════════════════╝"
                + ConsoleColors.RESET
        );
    }
}