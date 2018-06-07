import java.util.List;

public class Lecturer extends MevhanetUser {
    private List<CoursePerSemster> teachingCoursesList;
    private CoursePerSemster choosingCoursePerSemester(int course_id,String season,int year){return null;}
    private QuestionInRepo choosingQuestionFromTheRepository(String user_name,int course_Id,String Season,int year){return null;}
    private void createExam(String user_name,int course_Id,String Season){}
    private void addingQuestionToExam(int course_Id,String moed,int number,int points,String season,int year,int serial_number){}
    private void addQuestion(String content,int options,int level,String estimated_time){}
    private void addQuestionToRepository(QuestionInRepo question_in_repo){}

}
