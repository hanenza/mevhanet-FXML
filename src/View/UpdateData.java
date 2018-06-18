package View;

import Model.QuestionInRepo;
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

public class UpdateData {
    String ques;

    int serial;
    @FXML
    public ComboBox levelCombo;

    @FXML
    private TextField estimatedField;
    @FXML
    private TextField estimatedField0;

    @FXML
    private TextField optionsField;


    @FXML
    private CheckBox show;

    @FXML
    private Button submitButton3;

    public void handleSubmitButtonActionQuesDetail(ActionEvent event) throws IOException {
        if (estimatedField0.getText().isEmpty()) {
            ques = Main.question;
        }else {
            ques=estimatedField0.getText();
        }
        serial=Main.questionid;
        Pattern timeattern = Pattern.compile("([0-9]+){2}$");
        Matcher timeMatch = timeattern.matcher(estimatedField.getText());
        int myLevel=0;

        Window owner = submitButton3.getScene().getWindow();
        QuestionInRepo questionInRepo=new QuestionInRepo();
        String es=estimatedField.getText();
        if(estimatedField.getText().isEmpty()){
            String sql = MessageFormat.format("SELECT estimated_time FROM question WHERE question_id = ''{0}''", serial);
            try (Connection conn = Main.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql);) {
                es=rs.getString("estimated_time");
                rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else{
            try {
                Integer.parseInt(estimatedField.getText());
            }
            catch (Exception e){
                Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        "Invalid time");
                return;
            }
        }

        if(levelCombo.getSelectionModel().isEmpty()){
            String sql = MessageFormat.format("SELECT level FROM question WHERE question_id = ''{0}''", serial);
            try (Connection conn = Main.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql);) {
                myLevel=rs.getInt("level");
                rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            myLevel=Integer.parseInt((String)levelCombo.getValue());
        }
        questionInRepo.setSerial(serial);
        questionInRepo.setContent(ques);
        questionInRepo.setLevel(myLevel);
        questionInRepo.setTime(Integer.parseInt(es));

      if(Main.questionInRepo.updateQues(questionInRepo,owner)){
          Alerts.showAlert(Alert.AlertType.INFORMATION, owner, "Form Success!",
                  "Details are saved successfuly!!");}

    }
    @FXML
    public void handleAnswers(ActionEvent event) throws IOException {
        Main.serialNumber=Main.questionid;
        Parent detail_page = FXMLLoader.load(getClass().getResource("addAnwers.fxml"));
        Scene detail_scene = new Scene (detail_page );
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Add Answers!");
        app_stage.setScene(detail_scene);
        app_stage.show();
    }
    @FXML
    public void handleupAnswers(ActionEvent event) throws IOException {
        Main.serialNumber=Main.questionid;
        Parent detail_page = FXMLLoader.load(getClass().getResource("updateAnswers.fxml"));
        Scene detail_scene = new Scene (detail_page );
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("chooseAnswers!");
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
}
