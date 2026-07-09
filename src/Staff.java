package model;

import util.ConsoleColors;
import util.ConsoleUI;
// MEMBER 4 - Staff Workflow & Core OOP Architecture
public class Staff extends Person {

    private String department;

    // MEMBER 4 - Staff object creation
    public Staff(String id, String name, String phone,
                 String username, String password,
                 String department) {
        super(id, name, phone, username, password);
        this.department = department;
    }

    // MEMBER 4 - Department access
    public String getDepartment() {
        return department;
    }

    // MEMBER 4 - Department update
    public void setDepartment(String department) {
        this.department = department;
    }

    // MEMBER 4 - Staff menu display

    @Override
    public void showMenu() {

        ConsoleUI.printHeader("STAFF WORKSPACE");

        System.out.println(
                ConsoleColors.BRIGHT_GREEN +
                "║  ① View Assigned Complaints                                       ║\n" +
                "║  ② Update Progress                                                ║\n" +
                "║  ③ Mark Resolved                                                  ║\n" +
                "║  ④ View Completed Complaints                                      ║\n" +
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