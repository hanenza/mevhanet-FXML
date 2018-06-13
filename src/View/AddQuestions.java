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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;

public class AddQuestions {

    public int seq;

    @FXML
    private TextField quesField;
    @FXML
    private Button submitButton2;

    @FXML
    public void handleSubmitButtonActionQues(ActionEvent event) throws IOException {
        Window owner = submitButton2.getScene().getWindow();
        if(quesField.getText().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter question!!");
            return;
        }
      Main.question = quesField.getText();

          String sql=  "SELECT question_id FROM question";

        try (Connection conn = Main.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql) ){

            while(rs.next()){

                seq=rs.getInt("question_id");

            }
            seq++;
            Main.serialNumber=seq;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Parent detail_page = FXMLLoader.load(getClass().getResource("questionDetail.fxml"));
        Scene detail_scene = new Scene (detail_page );
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Question Details");
        app_stage.setScene(detail_scene);
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
