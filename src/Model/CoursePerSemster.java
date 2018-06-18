package Model;

import java.util.Date;
import java.util.List;

public class CoursePerSemster {

    private Notification notifications;
    private List<Student> students;
    private Date semester_date;
    private Exam[] exams=new Exam[3];
    private List<QuestionInRepo> questions;
    private Lecturer[] lecturersOfCourse=new Lecturer[5];
    private Teatching_Assistant[] teatching_assistants=new Teatching_Assistant[3];
    public void add(QuestionInRepo q){}
    public void addingQuestion (String moed,int number,int points, int serial_number){}
    public void getCoursePerSemester(int course_id, String season){}
    public  Exam getExam(String moed){return null;}
    public List<Exam> getAllExams(){return null;}
    public CoursePerSemster getCoursesPerSemester(int course_id, String season,int year){return null;}
}
