package util;

public class ConsoleUI {

    // Main Banner
	public static void printMainBanner() {

	    System.out.println();

	    System.out.println(
	            ConsoleColors.BRIGHT_CYAN +
	            ConsoleColors.BOLD +

	            "                    ╔════════════════════════════════════════════════════════════════════╗\n" +
	            "                    ║                 HOSTEL COMPLAINT MANAGEMENT SYSTEM                 ║\n" +
	            "                    ╚════════════════════════════════════════════════════════════════════╝"

	            + ConsoleColors.RESET
	    );

	    System.out.print(ConsoleColors.BRIGHT_YELLOW);

	    typeWriter(
	            "                                     Manage Complaints Efficiently",
	            15
	    );

	    System.out.println(ConsoleColors.RESET);
	}

    // Startup Art Animation
	public static void showStartupArt() {

	    String[] lines = AsciiArt.HOSTEL_ART.split("\n");

	    int terminalWidth = 140;

	    for (String line : lines) {

	        int visibleLength = line.replaceAll("\u001B\\[[;\\d]*m", "").length();

	        int padding = Math.max(0, (terminalWidth - visibleLength) / 2);

	        String spaces = new String(new char[padding]).replace('\0', ' ');

	        System.out.println(spaces + line);

	        try {
	            Thread.sleep(90);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }
	}

    // Typewriter Effect
    public static void typeWriter(String text, int delay) {

        for (char ch : text.toCharArray()) {
            System.out.print(ch);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println();
    }

    // Animated Header
    public static void printHeader(String title) {

        System.out.println();

        System.out.println(
                ConsoleColors.BRIGHT_GREEN +
                "╔════════════════════════════════════════════════════════════════════╗"
        );

        System.out.printf(
                "║ %-66s ║\n",
                title.toUpperCase()
        );

        System.out.println(
                "╚════════════════════════════════════════════════════════════════════╝"
                + ConsoleColors.RESET
        );
    }

    // Fancy Separator
    public static void printSeparator() {

        System.out.println(
                ConsoleColors.BRIGHT_CYAN +
                "     ══════════════════════════════════════════════════════════════════════"
                + ConsoleColors.RESET
        );
    }

    // Success Message
    public static void printSuccess(String message) {

        System.out.println(
                ConsoleColors.SUCCESS +
                message +
                ConsoleColors.RESET
        );
    }

    // Error Message with Blink
    public static void printError(String message) {

        System.out.println(
                ConsoleColors.ERROR +
                message +
                ConsoleColors.RESET
        );
    }

    // Warning Message
    public static void printWarning(String message) {

        System.out.println(
                ConsoleColors.WARNING +
                message +
                ConsoleColors.RESET
        );
    }

    // Info Message
    public static void printInfo(String message) {

        System.out.println(
                ConsoleColors.INFO +
                message +
                ConsoleColors.RESET
        );
    }

    // Premium Loading Bar
    public static void loadingEffect(String message) {

        System.out.print(
                ConsoleColors.BRIGHT_YELLOW +
                message + " "
        );

        try {

            for (int i = 0; i < 20; i++) {
                System.out.print("█");
                Thread.sleep(80);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
                " DONE" +
                ConsoleColors.RESET
        );
    }

    // Table Border
    public static void printTableBorder() {

        System.out.println(
                ConsoleColors.BRIGHT_PURPLE +
                "╔════════════╦══════════════════════════════╦════════════════╗"
                + ConsoleColors.RESET
        );
    }

    // Dramatic Pause
    public static void pause(int millis) {

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}