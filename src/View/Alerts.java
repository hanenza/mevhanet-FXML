package View;

import javafx.stage.Window;
import javafx.scene.control.Alert;


/**
 * Created by User on 07-Jun-18.
 */
public class Alerts {

    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
