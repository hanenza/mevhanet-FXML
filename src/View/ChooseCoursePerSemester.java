package View;

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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseCoursePerSemester  {
    private LinkedList<Integer> courses=new LinkedList<>();

    private String courseName;
    @FXML
    private ComboBox<String> coursesCombo ;
    @FXML
    private ListView<String> viewCourse = new ListView<String>();

    @FXML
    private Button submitButton1;

    private List<String> names=new ArrayList<>();

    public void upquestion(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        Window owner = submitButton1.getScene().getWindow();

        courseName= viewCourse.getSelectionModel().getSelectedItem();
        if (courseName==null||courseName==""){

            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please choose course!");
            return;
        }else{

            Parent question_page = FXMLLoader.load(getClass().getResource("UpdateQuestion.fxml"));
            String sql = MessageFormat.format("SELECT course_id FROM course WHERE course_name = ''{0}''", courseName);
            Connection c= Main.connect();
            Statement s = c.createStatement();

            // Statement s = null;

            ResultSet rs    = s.executeQuery(sql) ;
            Main.courseID1=rs.getInt("course_id");
            s.close();
            //c.close();
            // c.close();
            Scene upquestion_scene = new Scene (question_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setTitle("update question");
            app_stage.setScene(upquestion_scene);
            app_stage.show();
        }
    }
    public void getCoursesPerSemester() {
        // coursesCombo=new ComboBox();
        String sql="SELECT course_id FROM course";
        try (Connection conn = Main.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql) ) {
            while(rs.next()){
                courses.add(new Integer(rs.getInt("course_id")));
                getCourseName(rs.getInt("course_id"));
            }
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
    public void getCourseName(int id){
        String sql = "SELECT course_name"+" FROM course WHERE course_id=? ";
        try (Connection conn = Main.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql) ){
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()){

                if(!names.contains(rs.getString("course_name"))){
                    names.add(rs.getString("course_name"));
                }
            }
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
