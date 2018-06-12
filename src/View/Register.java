package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by User on 07-Jun-18.
 */
public class Register {
    String username;
    @FXML
    private TextField nameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private Button submitButton;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {

        this.username = Main.username;
        Window owner = submitButton.getScene().getWindow();
        Pattern namePattern = Pattern.compile("([a-zA-Z]*){1,100}$");
        Pattern phonePattern = Pattern.compile("([0-9]+){10}$");
        Pattern idPattern = Pattern.compile("([0-9]+){9}$");
        Pattern emailPattern = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        Matcher firstNameMatch = namePattern.matcher(nameField.getText());
        Matcher lastNameMatch = namePattern.matcher(lastNameField.getText());
        Matcher phoneMatch = phonePattern.matcher(phoneField.getText());
        Matcher idMatch = idPattern.matcher(idField.getText());
        Matcher emailMatch = emailPattern.matcher(emailField.getText());


        if(nameField.getText().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your first name");
            return;
        }
        else if(! firstNameMatch.matches()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please insert valid first name: The first name is between 1 and 100 characters. " +
                            "The first name can only contains an a-z (ignore case) character");
            return;
        }
        if(lastNameField.getText().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your last name");
            return;
        } else if(! lastNameMatch.matches()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please insert valid last name: The last name is between 1 and 100 characters. " +
                            "The last name can only contains an a-z (ignore case) character");
            return;
        }

        if(idField.getText().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }else if(! idMatch.matches()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Invalid ID");
            return;
        }

        if(addressField.getText().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your address");
            return;
        }
        if(phoneField.getText().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your phone");
            return;
        }
        else if(! phoneMatch.matches()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Phone number: 10 digits only");
            return;
        }
        if(emailField.getText().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email");
            return;
        }
        else if(! emailMatch.matches()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Invalid email!");
            return;
        }

        Random r = new java.util.Random ();
        String password = Long.toString (r.nextLong () & Long.MAX_VALUE, 36);


        String sql = "INSERT INTO users(username,password,id,first_name,last_name,phone,email,address) " +
                "VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.username);
            pstmt.setString(2, password);
            pstmt.setString(3, idField.getText());
            pstmt.setString(4, nameField.getText());
            pstmt.setString(5, lastNameField.getText());
            pstmt.setString(6, phoneField.getText());
            pstmt.setString(7, emailField.getText());
            pstmt.setString(8, addressField.getText());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Main.email = emailField.getText();
        Main.password = password;
        Parent register_page = FXMLLoader.load(getClass().getResource("SendEmail.fxml"));
        Scene register_scene = new Scene (register_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Send Email");
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
