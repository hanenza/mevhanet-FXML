package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public void pressRegister(ActionEvent event) throws IOException {
        Parent register_page = FXMLLoader.load(getClass().getResource("checkUserName.fxml"));
        Scene register_scene = new Scene (register_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Mevhanet - Register");
        app_stage.setScene(register_scene);
        app_stage.show();

    }
    public void pressChooseCourse(ActionEvent event) throws IOException {
        Parent chooseCourse_page = FXMLLoader.load(getClass().getResource("chooseCourse.fxml"));
        Scene chooseCourse_scene = new Scene (chooseCourse_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setTitle("Mevhanet - chooseCourse");
        app_stage.setScene( chooseCourse_scene);
        app_stage.show();

    }
}
