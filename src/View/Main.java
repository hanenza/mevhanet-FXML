package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.security.x509.SerialNumber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main extends Application {
    public static String username;
    public static String email;
    public static String password;
    public static String courseName;
    public static int serialNumber;
    public static String question;
    public static int courseID;
    public static int serialQuesInRepo;
    public static int options;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("myView.fxml"));
        primaryStage.setTitle("Mevhanet");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static Connection connect() throws ClassNotFoundException {
        // SQLite connection string
        String url = "jdbc:sqlite:C:/Users/Hanen/IdeaProjects/mevhanet-FXML/src/Model/Mevhanet.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
