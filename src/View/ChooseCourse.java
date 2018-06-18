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

public class ChooseCourse  {
    private LinkedList<Integer> courses=new LinkedList<>();

    private String courseName;
    @FXML
    private ComboBox<String> seasonCombo ;
    @FXML
    private ComboBox<String> yearCombo ;
    @FXML
    private ListView<String> viewCourse = new ListView<String>();
    @FXML
    private Button submitButton1;

    private List<String> names=new ArrayList<>();

    public void getRepo(ActionEvent event) throws IOException{
        Window owner = submitButton1.getScene().getWindow();
        int year=Integer.parseInt((String)yearCombo.getValue());
        String season=seasonCombo.getValue();
        courseName=viewCourse.getSelectionModel().getSelectedItem();
        if (courseName==null||courseName==""){

            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please choose course!");
            return;
        }else{
            String sql1 = MessageFormat.format("SELECT course_id FROM course WHERE course_name = ''{0}''", courseName);
            try (Connection conn = Main.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql1)) {
                 Main.courseID=rs.getInt("course_id");

            }catch (SQLException e) {
                Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        e.getMessage());
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Parent question_page = FXMLLoader.load(getClass().getResource("addQuestion.fxml"));
            Main.courseName = courseName;
            Scene question_scene = new Scene (question_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setTitle("add Question to course");
            app_stage.setScene(question_scene);
            app_stage.show();
        }
    }
    public void getCoursesPerSemester() {
       // coursesCombo=new ComboBox();
        Window owner = submitButton1.getScene().getWindow();
        int year=Integer.parseInt((String)yearCombo.getValue());
        String season=seasonCombo.getValue();
        String sql = "SELECT course_id FROM course_per_semester WHERE season LIKE"+"'"+season+"'"+"AND year LIKE"+"'"+year+"'"+";";
       if(yearCombo.getValue().isEmpty()){
           if(seasonCombo.getValue().isEmpty()){
               Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                       "Please choose year and season!");
               return;
           }
           Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                   "Please choose year!");
           return;

       }
        if(seasonCombo.getValue().isEmpty()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please choose season!");
            return;
        }
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
            if(names.size()==0){
                Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        "no courses at this season and year please choose another!");
                return;
            }
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
