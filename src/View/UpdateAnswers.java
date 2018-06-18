package View;

import Model.Answer;
import Model.CoursePerSemster;
import javafx.collections.ObservableList;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import javax.swing.text.MaskFormatter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateAnswers  {
    private LinkedList<Integer> question=new LinkedList<>();

    private String myanswer;
    @FXML
    private ComboBox<String> coursesCombo ;
    @FXML
    private ListView<String> viewCourse = new ListView<String>();

    @FXML
    private Button submitButton12;

    private List<String> names=new ArrayList<>();


    public void upquestion(ActionEvent event) throws IOException{
        Window owner = submitButton12.getScene().getWindow();


        myanswer= viewCourse.getSelectionModel().getSelectedItem();
        if (myanswer==null||myanswer==""){

            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please choose Answer!");
            return;
        }else{
            String sql = MessageFormat.format("SELECT * FROM answers WHERE text = ''{0}''", myanswer);
            try (Connection conn = Main.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)) {
                Main.answerseq=rs.getInt("seq");
                Main.answer=myanswer;
                rs.close();
                stmt.close();
                //conn.close();
            }catch (SQLException e) {
                Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        e.getMessage());
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Parent question_page = FXMLLoader.load(getClass().getResource("UpdateTheAnswer.fxml"));
            Main.serialNumber=Main.questionid;
            Scene upquestion_scene = new Scene (question_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setTitle("Update The Answer");
            app_stage.setScene(upquestion_scene);
            app_stage.show();
        }
    }
    public void getqustion() {
        // coursesCombo=new ComboBox();
        String sql1 = MessageFormat.format("SELECT question_id ,text  from answers WHERE question_id = ''{0}''", Main.questionid);

        //  System.out.println(sql1);
        // String sql="SELECT question_id FROM question,questionRepo ";
        try (Connection conn = Main.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql1) ) {
            while(rs.next()){
                // System.out.println(rs.getBoolean("showed"));

                //System.out.println(rs.getInt("question_id"));
                question.add(new Integer(rs.getInt("question_id")));
                getanswertext(rs.getInt("question_id"));

            }
            rs.close();
            // conn.close();
            //      coursesCombo.getItems().addAll(names);
            ObservableList<String> items =FXCollections.observableArrayList (
                    names);
            viewCourse.setItems(items);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void getanswertext(int id){
        String sql = "SELECT text"+" FROM answers WHERE question_id=? ";
        try (Connection conn = Main.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql) ){
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()){

                if(!names.contains(rs.getString("text"))){
                    names.add(rs.getString("text"));
                }
            }
            rs.close();
            //names.add(rs.getString("course_name"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("sql 2");
            e.printStackTrace();
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
