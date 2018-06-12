package Model;

import View.Alerts;
import View.Main;
import javafx.scene.control.Alert;
import javafx.stage.Window;

import java.sql.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MevhanetUser extends User {
    private String user_name;
    private String password;
    public void  updateUsernameDetails(String first_name, String last_name, int ID,  String address,String phone,String email){}
    public void create(String user_name){}
    public boolean addUser(MevhanetUser user, Window owner){
        Pattern namePattern = Pattern.compile("([a-zA-Z]*){1,100}$");
        Pattern phonePattern = Pattern.compile("([0-9]+){10}$");
        Pattern idPattern = Pattern.compile("([0-9]+){9}$");
        Pattern emailPattern = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        Matcher firstNameMatch = namePattern.matcher(user.getFirstName());
        Matcher lastNameMatch = namePattern.matcher(user.getLastName());
        Matcher phoneMatch = phonePattern.matcher(user.getPhone());
        Matcher idMatch = idPattern.matcher(user.getId());
        Matcher emailMatch = emailPattern.matcher(user.getEmail());


        if(user.getFirstName().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your first name");
            return false;
        }
        else if(! firstNameMatch.matches()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please insert valid first name: The first name is between 1 and 100 characters. " +
                            "The first name can only contains an a-z (ignore case) character");
            return false;
        }
        if(user.getLastName().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your last name");
            return false;
        } else if(! lastNameMatch.matches()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please insert valid last name: The last name is between 1 and 100 characters. " +
                            "The last name can only contains an a-z (ignore case) character");
            return false;
        }

        if(user.getId().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return false;
        }else if(! idMatch.matches()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Invalid ID");
            return false;
        }

        if(user.getAddress().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your address");
            return false;
        }
        if(user.getAddress().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your phone");
            return false;
        }
        else if(! phoneMatch.matches()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Phone number: 10 digits only");
            return false;
        }
        if(user.getEmail().isEmpty()) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email");
            return false;
        }
        else if(! emailMatch.matches()){
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Invalid email!");
            return false;
        }

        Random r = new java.util.Random ();
        String password = Long.toString (r.nextLong () & Long.MAX_VALUE, 36);


        String sql = "INSERT INTO users(username,password,id,first_name,last_name,phone,email,address) " +
                "VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUser_name());
            pstmt.setString(2, password);
            pstmt.setString(3, user.getId());
            pstmt.setString(4, user.getFirstName());
            pstmt.setString(5, user.getLastName());
            pstmt.setString(6, user.getPhone());
            pstmt.setString(7, user.getEmail());
            pstmt.setString(8, user.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Main.user.setEmail(user.getEmail());
        Main.user.setPassword(password);

        return true;
    }
    public boolean checkIfUsernameExist(String user_name, Window owner){

        String sql = MessageFormat.format("SELECT COUNT(*) FROM users WHERE username = ''{0}''", user_name);

        try (Connection conn = Main.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            rs.next();
            int rowCount = rs.getInt(1);

            if(rowCount!=0) {
                Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        "Username already exist!");
                return false;
            }
        } catch (SQLException e) {
            Alerts.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return true;
    }
    private void updateUserDetails(String first_name, String last_name, int ID, String address, String phone, String email){}
    private List<Course> GetCourses(){return null;}

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

