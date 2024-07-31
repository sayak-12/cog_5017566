public class Logger {
    // Private static instance of the class
    private static Logger instance;

    // Private constructor to prevent instantiation
    private Logger() {
    }

    // Public static method to provide access to the instance
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    // Example method to demonstrate usage
    public void log(String message) {
        System.out.println("Log message: " + message);
    }
}
