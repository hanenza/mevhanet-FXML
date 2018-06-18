package Model;
import javafx.collections.ObservableList;
import javafx.stage.Window;

import java.awt.*;
import java.util.Observable;

public class Model {
   private MevhanetUser mevhanetUser;
   private QuestionInRepo questionInRepo;
   private Answer answer;
    public boolean addUser(MevhanetUser user, Window owner){
        return mevhanetUser.addUser(user,owner);
    }
    public boolean checkIfUsernameExist(String user_name, Window owner){
        return mevhanetUser.checkIfUsernameExist(user_name,owner);
    }
    public boolean addQues(QuestionInRepo questionInRepo,Window owner){
        return  this.questionInRepo.addQuestion(questionInRepo,owner);
    }
    public  boolean addNewAnswer(Answer ans,Window owner){
        return answer.addNewAnwer(ans,owner);
    }
    public boolean editAnswer(Answer ans, Window owner){
        return answer.updateAnswer(ans,owner);
    }
    public boolean editQuestion(QuestionInRepo questionInRepo, Window owner){
        return questionInRepo.updateQues(questionInRepo,owner);
    }
}
