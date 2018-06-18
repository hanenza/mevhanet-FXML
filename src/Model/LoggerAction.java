package Model;

public class LoggerAction {
    private static LoggerAction ourInstance = new LoggerAction();

    public static LoggerAction getInstance() {
        return ourInstance;
    }

    private LoggerAction() {
    
    }
}
