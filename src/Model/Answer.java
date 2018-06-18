package Model;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import View.Alerts;
import View.Main;
import javafx.scene.control.Alert;
import javafx.stage.Window;

public class Answer {
    private String  correct;
    private int ans_id;
    private int question_id;
    private String content;

    public String  isCorrect() {
        return correct;
    }

    public int getAns_id() {
        return ans_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public String getContent() {
        return content;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public void setAns_id(int ans_id) {
        this.ans_id = ans_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean addNewAnwer(Answer answer,Window owner){
        if(answer.getContent().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter contact For the qestion!!");
            return false;
        }

        String sql = "INSERT INTO answers(seq,question_id,correct,text) " +
                "VALUES(?,?,?,?)";
        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, answer.getAns_id());
            pstmt.setInt(2, answer.getQuestion_id());
            pstmt.setString(3, answer.isCorrect());
            pstmt.setString(4, answer.getContent());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
}
