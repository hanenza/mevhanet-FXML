package View;

import Model.QuestionInRepo;
import javafx.collections.FXCollections;
import Model.CoursePerSemster;
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
import java.util.Optional;
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
        //getSeq();
        Window owner = submitButton3.getScene().getWindow();
        try {
            Integer.parseInt(estimatedField.getText());
        }
        catch (Exception e){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Invalid time");
            return;
        }
        if(estimatedField.getText().isEmpty()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Invalid time");
            return;
        }
        else if(Integer.parseInt(estimatedField.getText())<1){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Invalid time");
            return;
        }
        if(levelCombo.getSelectionModel().isEmpty()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "please choose level");
            return;
        }
        QuestionInRepo quesRepo = new QuestionInRepo();
        ques = Main.question;
        serial = Main.serialNumber;
        int myLevel = 0;
        myLevel = Integer.parseInt((String) levelCombo.getValue());
        quesRepo.setContent(Main.question);
        quesRepo.setSerial(Main.serialNumber);
        quesRepo.setCourseID(Main.courseID);
        quesRepo.setReposeq(getSeq());
        quesRepo.setLevel(myLevel);
        quesRepo.setTime(Integer.parseInt(estimatedField.getText()));
        quesRepo.setOptions(0);
        Pattern timeattern = Pattern.compile("([0-9]+){2}$");
        Matcher timeMatch = timeattern.matcher(estimatedField.getText());

        if (Main.questionInRepo.addQuestion(quesRepo, owner)) {
            confirmationAlert(event);
        }
    }

    private int getSeq(){
        String sql=  "SELECT seq FROM questionRepo";
        int sequance=0;
        try (Connection conn = Main.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql) ){

            while(rs.next()){
                sequance=rs.getInt("seq");

            }
            sequance++;

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return sequance;
    }
    @FXML
    public void handleAnswers(ActionEvent event) throws IOException {
        Parent detail_page = FXMLLoader.load(getClass().getResource("addAnwers.fxml"));
        Scene detail_scene = new Scene (detail_page );
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Add Answers!");
        app_stage.setScene(detail_scene);
        app_stage.show();
    }
    @FXML
    public void handleHomePage(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource("myView.fxml"));
        Scene register_scene = new Scene (register_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Mevhanet");
        app_stage.setScene(register_scene);
        app_stage.show();
    }
    private void confirmationAlert(ActionEvent event)throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Details saved successfuly!");
        alert.setContentText("if you want to add more answers press ok ! else if you want to back to home page press cencel ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent detail_page = FXMLLoader.load(getClass().getResource("addAnwers.fxml"));
            Scene detail_scene = new Scene(detail_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setTitle("Add Answers!");
            app_stage.setScene(detail_scene);
            app_stage.show();
        } else {
            handleHomePage(event);
        }
    }
}
