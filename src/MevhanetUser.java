import java.util.List;

public abstract class MevhanetUser extends User {
    private String user_name;
    private String password;
    private void  updateUsernameDetails(String first_name, String last_name, int ID,  String address,String phone,String email){}
    private  void  create(String user_name){}
    private void addUser(User userObject){}
    private void checkIfUsernameExist(String user_name){}
    private void updateUserDetails(String first_name, String last_name, int ID, String address, String phone, String email){}
    private List<Course> GetCourses(){return null;}

}

