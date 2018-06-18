package Model;

import View.Alerts;
import View.Main;
import javafx.scene.control.Alert;
import javafx.stage.Window;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class QuestionInRepo {
    private int options;
    private int  level;
    private int estimated_time;
    private int serial;;
    private String content;
    private int course_id;
    private int repoSer;
    private List<Answer>answers;
    private Comment[] comments=new Comment[10];

    public void setOptions(int options){

        this.options=options;
    }
    public int getOptions(){
        return options;
    }
    public void setLevel(int level){
        this.level=level;
    }
    public int getLevel(){
        return level;
    }
    public void setTime(int time){
        this.estimated_time=time;
    }
    public int getTime(){
        return estimated_time;
    }
    public void setReposeq(int seq){
        this.repoSer=seq;
    }
    public int getRepoSer(){
        return repoSer;
    }
    public void setSerial(int serial){
        this.serial=serial;
    }
    public int getSerial(){
        return serial;
    }
    public void setContent(String content){
        this.content=content;
    }
    public String getContent(){
        return content;
    }
    public void setCourseID(int id){
        this.course_id=id;
    }
    public int getCourseID(){
        return course_id;
    }

    public boolean addQuestion(QuestionInRepo question,Window owner){
        if(question.getTime()==0){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please fill how many time this question need!");
            return false;
        }else if(question.getTime()<1){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Invalid time");
            return false;
        }
        if(question.getLevel()==0){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please select level to this question");
        }


        String sql = "INSERT INTO question(question_id,contact,level,estimated_time,options) " +
                "VALUES(?,?,?,?,?)";
        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, question.getSerial());
            pstmt.setString(2, question.getContent());
            pstmt.setInt(3, question.getLevel());
            pstmt.setInt(4, question.getTime());
            pstmt.setInt(4, 0);
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
            pstmt1.setInt(1,question.getRepoSer() );
            pstmt1.setInt(2, question.getSerial());
            pstmt1.setInt(3,question.getCourseID());
            pstmt1.setString(4, "FALSE");
            pstmt1.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return  true;
    }

}
