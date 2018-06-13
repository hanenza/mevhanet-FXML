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
        Pattern timeattern = Pattern.compile("([0-9]+){2}$");
        Matcher timeMatch = timeattern.matcher(estimatedField.getText());
        int myLevel=0;

        Window owner = submitButton3.getScene().getWindow();

        if(estimatedField.getText().isEmpty()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please fill how many time this question need!");
            return;
        }else if(! timeMatch.matches()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Invalid time");
            return;
        }
        if(levelCombo.getSelectionModel().isEmpty()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please select level to this question");
        }

        myLevel=Integer.parseInt((String)levelCombo.getValue());



        String sql = "INSERT INTO question(question_id,contact,level,estimated_time) " +
                "VALUES(?,?,?,?)";
        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, serial);
            pstmt.setString(2, ques);
            pstmt.setInt(3, myLevel);
            pstmt.setString(4, estimatedField.getText());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql1 = "INSERT INTO questionRepo(seq,question_id,course_id,showed) " +
                "VALUES(?,?,?,?)";

        try (Connection conn1 = Main.connect();
             PreparedStatement pstmt1 = conn1.prepareStatement(sql1)) {
          //  System.out.println(Main.serialNumber);
            pstmt1.setInt(1, getSeq());
            pstmt1.setInt(2, Main.serialNumber);
            pstmt1.setInt(3, Main.courseID);
            pstmt1.setString(4, "FALSE");
            pstmt1.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Alerts.showAlert(Alert.AlertType.INFORMATION, owner, "Form Success!",
                "Details are saved successfuly!!");
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
       /* Parent register_page = FXMLLoader.load(getClass().getResource("addAnswers.fxml"));
        Scene register_scene = new Scene (register_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Add Answers");
        app_stage.setScene(register_scene);
        app_stage.show();*/
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
}
