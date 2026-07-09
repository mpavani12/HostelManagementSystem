package util;

public class ConsoleColors {

    // Reset
    public static final String RESET = "\u001B[0m";

    // Colors
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Bright Colors
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";

    // Effects
    public static final String BOLD = "\033[1m";
    public static final String UNDERLINE = "\u001B[5m";
    public static final String BLINK = "\033[5m";
    public static final String REVERSE = "\033[7m";

    // Messages
    public static final String SUCCESS =
            BRIGHT_GREEN + BOLD + "✔ ";

    public static final String ERROR =
            BRIGHT_RED + BOLD + BLINK + "✖ ";

    public static final String WARNING =
            BRIGHT_YELLOW + BOLD + "⚠ ";

    public static final String INFO =
            BRIGHT_CYAN + BOLD + "➜ ";
}