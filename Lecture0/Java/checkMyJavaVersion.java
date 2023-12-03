public class checkMyJavaVersion {
    public static void main(String[] args) {
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("Java runtime version: " + System.getProperty("java.runtime.version"));
        System.out.println("Java home: " + System.getProperty("java.home"));
        System.out.println("Java vendor: " + System.getProperty("java.vendor"));
        System.out.println("Java vendor URL: " + System.getProperty("java.vendor.url"));
        System.out.println("Java class path: " + System.getProperty("java.class.path"));
    }
}
