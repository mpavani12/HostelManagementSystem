package util;

// MEMBER 1 - Authentication & Input Validation
public class ValidationUtil {

    // MEMBER 1 - Empty field validation
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }
    public static boolean isValidName(String name) {

        if (name == null) {
            return false;
        }

        return name.matches("^[A-Za-z][A-Za-z]{4,}$");
    }
    

    // MEMBER 1 - Username validation
    public static boolean isValidUsername(String username) {

        if (username == null || username.length() < 6) {
            return false;
        }

        // must start with alphabet
        if (!Character.isLetter(username.charAt(0))) {
            return false;
        }

        // only letters and digits
        for (char ch : username.toCharArray()) {

            if (!Character.isLetterOrDigit(ch)) {
                return false;
            }
        }

        return true;
    }

    // MEMBER 1 - Improved phone validation (India)
    public static boolean isValidPhone(String phone) {

        if (phone == null || phone.length() != 10) {
            return false;
        }

        for (char ch : phone.toCharArray()) {

            if (!Character.isDigit(ch)) {
                return false;
            }
        }

        char first = phone.charAt(0);

        return first == '6' || first == '7' || first == '8' || first == '9';
    }

    // MEMBER 1 - Room number validation
    public static boolean isValidRoomNumber(String roomNumber) {

        if (roomNumber == null || roomNumber.isEmpty()) {
            return false;
        }

        for (char ch : roomNumber.toCharArray()) {

            if (!Character.isDigit(ch)) {
                return false;
            }
        }

        return true;
    }

    // MEMBER 1 - Detailed password validation
    public static String validatePassword(String password) {

        if (password == null || password.length() < 6) {
            return "Password must contain minimum 6 characters.";
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char ch : password.toCharArray()) {

            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            }
            else if (Character.isLowerCase(ch)) {
                hasLower = true;
            }
            else if (Character.isDigit(ch)) {
                hasDigit = true;
            }
            else {
                hasSpecial = true;
            }
        }

        if (!hasUpper) {
            return "Password must contain at least 1 uppercase letter.";
        }

        if (!hasLower) {
            return "Password must contain at least 1 lowercase letter.";
        }

        if (!hasDigit) {
            return "Password must contain at least 1 digit.";
        }

        if (!hasSpecial) {
            return "Password must contain at least 1 special character.";
        }

        return null;
    }

    // Keep old method for compatibility
    public static boolean isValidPassword(String password) {
        return validatePassword(password) == null;
    }

    // MEMBER 1 - Generic menu choice validation
    public static boolean isValidMenuChoice(int choice, int min, int max) {
        return choice >= min && choice <= max;
    }

    // MEMBER 1 - Complaint category validation
    public static boolean isValidComplaintCategory(int choice) {
        return choice >= 1 && choice <= 5;
    }

    // MEMBER 1 - Complaint category mapping
    public static String getCategoryName(int choice) {
        switch (choice) {
            case 1:
                return "Electrical";
            case 2:
                return "Plumbing";
            case 3:
                return "Wi-Fi";
            case 4:
                return "Cleaning";
            case 5:
                return "Furniture";
            default:
                return "Invalid";
        }
    }
}