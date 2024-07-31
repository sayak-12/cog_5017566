public class test {
    public static void main(String[] args) {
        // Get the single instance of Logger
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        // Check if both references point to the same instance
        System.out.println("logger1 == logger2: " + (logger1 == logger2)); // Should print true

        // Use the Logger
        logger1.log("Singleton pattern test.");
    }
}
