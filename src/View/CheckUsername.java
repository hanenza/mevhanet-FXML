package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 07-Jun-18.
 */
public class CheckUsername {
    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        Window owner = submitButton.getScene().getWindow();
        Pattern usrNamePtrn = Pattern.compile("[a-zA-Z0-9.\\\\-_]{3,}");
        Matcher uername_mtch = usrNamePtrn.matcher(nameField.getText());

        if(nameField.getText().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter username");
            return;
        }
        else if(! uername_mtch.matches()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Length >=3 and Valid characters: a-z, A-Z, 0-9, points, dashes and underscores.");
            return;
        }

        String sql = MessageFormat.format("SELECT COUNT(*) FROM users WHERE username = ''{0}''", nameField.getText());

        try (Connection conn = Main.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            rs.next();
            int rowCount = rs.getInt(1);

            if(rowCount!=0) {
                Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        "Username already exist!");
                return;
            }
        } catch (SQLException e) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    e.getMessage());
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Parent register_page = FXMLLoader.load(getClass().getResource("register.fxml"));
        Main.username = nameField.getText();
        Scene register_scene = new Scene (register_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Register");
        app_stage.setScene(register_scene);
        app_stage.show();

    }

    @FXML
    protected void handleHomePage(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource("myView.fxml"));
        Scene register_scene = new Scene (register_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Mevhanet");
        app_stage.setScene(register_scene);
        app_stage.show();
    }

}
