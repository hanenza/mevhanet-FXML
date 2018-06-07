import java.util.List;

public class Secretary extends User {
    private List<CoursePerSemster>courses;
    public void EditCourse(CoursePerSemster coursePerSemster){}
    public CoursePerSemster CreatCoursePerSemster(String moaed,int year){return null;}
    public Course OpenCourse(String moaed,int year){return null;}
    public void  finishAddingUser (String user_name){}
    public void insertUserDetails(String user_name,String first_name,String last_name,int ID,String address,String phone,String email){}
    public User insertUsername(String user_name){return null;}
    public void  addCourseMember(int course_id,String season,String user_name){}
    public void SetMember(String username){}
    public boolean  checkUsernameExist(String user_name){return false;}
    public User getUser(String user_name){return null;}
    public Course chooseExistingCourse(int course_id,String season,int year){return null;}
    public void SavenewCoursePerSemester(CoursePerSemster c){}
    public void  addCourseManager(int course_id,String season,String user_name){}
    public void SetManager(String username){}
    public void create(String user_name){}
}
