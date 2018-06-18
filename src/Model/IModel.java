package Model;

import javafx.stage.Window;

public interface IModel {
    public boolean addUser(MevhanetUser user, Window owner);

    public boolean checkIfUsernameExist(String user_name, Window owner);

    public boolean addQues(QuestionInRepo questionInRepo,Window owner);

    public  boolean addNewAnswer(Answer ans,Window owner);

    public boolean editAnswer(Answer ans, Window owner);
    public boolean editQuestion(QuestionInRepo questionInRepo, Window owner);

}
