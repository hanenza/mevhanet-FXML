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

public class AddAnswers {

    int questionID;
    @FXML
    private TextField textField;
    @FXML
    private CheckBox correctCheck;
    @FXML
    private Button submitButton5;
    @FXML
    public void handleSubmitButtonAction(ActionEvent event) throws IOException {
        questionID=Main.serialNumber;
        Window owner = submitButton5.getScene().getWindow();
        String choice="FALSE";
        if(correctCheck.isSelected()){
            choice="TRUE";
        }
        Answer answer=new Answer();
        answer.setContent(textField.getText());
        answer.setAns_id(getSeq());
        answer.setQuestion_id(Main.serialNumber);
        answer.setCorrect(choice);
        answer.setOption(getOptions());
      if(Main.ans.addNewAnwer(answer,owner)) {
          String sql1 = "UPDATE question SET options = ?";
          try (Connection conn1 = Main.connect();
               PreparedStatement pstmt1 = conn1.prepareStatement(sql1)) {
              pstmt1.setInt(1, getOptions());
              pstmt1.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
          } catch (ClassNotFoundException e) {
              e.printStackTrace();
          }
          confirmationAlert(event);
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


    private void confirmationAlert(ActionEvent event)throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("if you want to add more answers press ok else press cencel ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Parent detail_page = FXMLLoader.load(getClass().getResource("addAnwers.fxml"));
            Scene detail_scene = new Scene (detail_page );
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setTitle("Add Answers!");
            app_stage.setScene(detail_scene);
            app_stage.show();
        }
        else{
            Parent register_page = FXMLLoader.load(getClass().getResource("myView.fxml"));
            Scene register_scene = new Scene (register_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setTitle("Mevhanet");
            app_stage.setScene(register_scene);
            app_stage.show();
        }
    }
    }
