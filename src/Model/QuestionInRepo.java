package Model;

import java.util.List;

public class QuestionInRepo {
    private int options;
    private double level;
    private double estimated_time;
    private String content;
    private List<Answer>answers;
    private Comment[] comments=new Comment[10];
}
