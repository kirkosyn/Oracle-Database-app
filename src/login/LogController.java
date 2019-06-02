package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class LogController {
    // Preferences preferences=Preferences.userNodeForPackage(LogController.class);

    private static String Login = "LOGIN";
    private static String Password = "HASŁO";

    @FXML
    private PasswordField password;

    @FXML
    private TextField login;


    public void Connect(ActionEvent actionEvent) throws IOException {
        if (password.getText().equals("admin") && login.getText().equals("admin")) {

            ScreenController.activate("admin", "Menadżer pracowników", 750, 400);
        } else if (password.getText().equals("bbb") && login.getText().equals("bbb")) {

        }

        //gdy sie nie łaczy z baz danych
        else {
            password.clear();
            login.clear();
            password.setPromptText(Password);
            login.setPromptText(Login);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Podano złe hasło lub login");
            alert.setTitle("Bład Logowenia");
            alert.showAndWait();

        }

    }

    public static String getLogin() {
        return Login;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setLogin(String newLogin) {
        Login = newLogin;
    }

}
