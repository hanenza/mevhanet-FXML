package Model;

public class LoggerError  {
    private static LoggerError ourInstance = new LoggerError();

    public static LoggerError getInstance() {
        return ourInstance;
    }

    private LoggerError() {
    }
}
