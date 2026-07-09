package model;
import util.ConsoleColors;
import util.ConsoleUI;

// MEMBER 3 - Admin Operations
public class Admin extends Person {

    // MEMBER 3 - Admin object creation
    public Admin(String id, String name, String phone,
                 String username, String password) {
        super(id, name, phone, username, password);
    }

    // MEMBER 3 - Admin menu display
    @Override
    public void showMenu() {

        ConsoleUI.printHeader("ADMIN CONTROL PANEL");

        System.out.println(
                ConsoleColors.BRIGHT_GREEN + 
                "║  ① View All Complaints                                            ║\n" +
                "║  ② Assign Complaint                                               ║\n" +
                "║  ③ Search Complaint                                               ║\n" +
                "║  ④ Add Staff                                                      ║\n" +
                "║  ⑤ View Staff                                                     ║\n" +
                "║  ⑥ View Statistics                                                ║\n" +
                "║  ⑦ Logout                                                         ║"
                + ConsoleColors.RESET
        );

        System.out.println(
                ConsoleColors.BRIGHT_GREEN +
                "╚════════════════════════════════════════════════════════════════════╝"
                + ConsoleColors.RESET
        );
    }
}