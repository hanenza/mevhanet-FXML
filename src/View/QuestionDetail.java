package View;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionDetail {
    String ques;

    int serial;
    @FXML
    public ComboBox levelCombo;

    @FXML
    private TextField estimatedField;

    @FXML
    private TextField optionsField;


    @FXML
    private CheckBox show;

    @FXML
    private Button submitButton3;

    public void handleSubmitButtonActionQuesDetail(ActionEvent event) throws IOException {
        ques=Main.question;
        serial=Main.serialNumber;
        String showChoice="FALSE";
        int myLevel=0;

        Window owner = submitButton3.getScene().getWindow();


        if(optionsField.getText().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter how many options");
            return;

        }
        else if(Integer.parseInt(optionsField.getText())>5||Integer.parseInt(optionsField.getText())<2){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "please inter valid options to this question up to 5 options!");
            return;
        }
        if(estimatedField.getText().isEmpty()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please fill how many time this question need!");
            return;
        }
        if(levelCombo.getSelectionModel().isEmpty()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please select level to this question");
        }
        if(show.isSelected()){
            showChoice="TRUE";
        }

        myLevel=Integer.parseInt((String)levelCombo.getValue());



        String sql = "INSERT INTO question(question_id,contact,level,showed,options,estimated_time) " +
                "VALUES(?,?,?,?,?,?)";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, serial);
            pstmt.setString(2, ques);
            pstmt.setInt(3, myLevel);
            pstmt.setString(4, showChoice);
            pstmt.setString(5, optionsField.getText());
            pstmt.setString(6, estimatedField.getText());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       /* String sql1 = MessageFormat.format("SELECT COUNT(*) FROM question WHERE username = ''{0}''", serial);

        try (Connection conn = Main.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            rs.next();
            int rowCount = rs.getInt(1);

            if(rowCount!=0) {
                Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        "the serial number already exist!");
                return;
            }
        } catch (SQLException e) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    e.getMessage());
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        Parent register_page = FXMLLoader.load(getClass().getResource("addAnswers.fxml"));
        Scene register_scene = new Scene (register_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Add Answers");
        app_stage.setScene(register_scene);
        app_stage.show();
    }
    @FXML
    protected void handleHomePage(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource("addAnswers.fxml"));
        Scene register_scene = new Scene (register_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Answers");
        app_stage.setScene(register_scene);
        app_stage.show();
    }
}
