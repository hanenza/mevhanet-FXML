package View;

import Model.MevhanetUser;
import Model.Staff;
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

        this.username = Main.user.getUser_name();
        Window owner = submitButton.getScene().getWindow();

        MevhanetUser myUser = new Staff();
        myUser.setFirstName(nameField.getText());
        myUser.setLastName(lastNameField.getText());
        myUser.setPhone(phoneField.getText());
        myUser.setId(idField.getText());
        myUser.setEmail(emailField.getText());
        myUser.setAddress(addressField.getText());

        if (Main.user.addUser(myUser, owner)){
            Parent register_page = FXMLLoader.load(getClass().getResource("SendEmail.fxml"));
            Scene register_scene = new Scene (register_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setTitle("Send Email");
            app_stage.setScene(register_scene);
            app_stage.show();
        }
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
