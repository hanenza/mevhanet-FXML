package View;

import Model.Answer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Optional;

public class UpdateTheAnswer {

    int questionID;
    @FXML
    private TextField textField;
    @FXML
    private CheckBox correctCheck;
    @FXML
    private Button submitButton5;
    @FXML
    public void handleSubmitButtonAction(ActionEvent event) throws IOException {
        Answer ans=new Answer();
        //questionID=Main.questionid;
        questionID = Main.serialNumber;

        Window owner = submitButton5.getScene().getWindow();
        String mytext;
        if(textField.getText().isEmpty()) {
            mytext=Main.answer;
        }else {
            mytext=textField.getText();
        }
        String choice="FALSE";
        if(correctCheck.isSelected()){
            choice="TRUE";
        }
        if(Main.ans.updateAnswer(ans,owner)){
            backHome(event);
        }
    }

    private int getSeq(){
        String query=  "SELECT seq FROM answers";
        int seq=0;
        try (Connection conn = Main.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query) ){
            while(rs.next()){
                seq=rs.getInt("seq");
            }
            seq++;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return seq;
    }


    private int getOptions(){
        String sql = MessageFormat.format("SELECT options FROM question WHERE question_id = ''{0}''",Main.serialNumber);
        int op=0;
        try (Connection conn = Main.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql) ){
            while(rs.next()){
                op=rs.getInt("options");
            }
            op++;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return op;
    }


    private void backHome(ActionEvent event)throws IOException{

        Parent register_page = FXMLLoader.load(getClass().getResource("myView.fxml"));
        Scene register_scene = new Scene (register_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Mevhanet");
        app_stage.setScene(register_scene);
        app_stage.show();
    }

}
