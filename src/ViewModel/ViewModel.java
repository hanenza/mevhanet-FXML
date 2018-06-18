package ViewModel;
import javafx.collections.ObservableList;
import javafx.collections.ObservableList;
import Model.Model;
import Model.QuestionInRepo;
import java.awt.*;
import Model.*;
import java.util.Observable;
import java.util.Observer;
import javafx.stage.Window;

public class ViewModel extends Observable implements Observer {
    private Model model;

    public ViewModel(Model model) {
        this.model=model;
    }

    @Override
    public void update(Observable o, Object arg) {
        String s = (String) arg;
        if(s.substring(0,6).equals("Email:")){
            setChanged();
            notifyObservers(s.substring(6));
        }
    }
    public boolean addQues(QuestionInRepo questionInRepo, Window owner){
        return model.addQues(questionInRepo,owner);
    }
    public boolean addAnswer(Answer ans,Window owner){
        return model.addNewAnswer(ans,owner);
    }
    boolean editAns(Answer ans,Window owner){
        return model.editAnswer(ans,owner);
    }
    boolean editQues(QuestionInRepo ques,Window owner){
        return model.editQuestion(ques,owner);
    }
}
