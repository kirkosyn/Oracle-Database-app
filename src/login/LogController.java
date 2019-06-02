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
        /* TODO  zrobienie w tym miejscu by sie laczy z baza danych*/
        Stage stage = new Stage();
        Parent root;
        //domyslne hasla login aaa i aaa
        if (password.getText().equals("aaa") && login.getText().equals("aaa")) {
            stage = (Stage) password.getScene().getWindow(); //bierzemy jakikolwiek element by ze starego okna by na niego naniesc nowe

            root = FXMLLoader.load(getClass().getResource("pracownik.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if (password.getText().equals("bbb") && login.getText().equals("bbb")) {
            stage = (Stage) password.getScene().getWindow(); //bierzemy jakikolwiek element by ze starego okna by na niego naniesc nowe

            root = FXMLLoader.load(getClass().getResource("adminstrator.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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
    public static String getLogin()
    {return Login;}
    public static String getPassword()
    {return  Password;}
    public static void setLogin(String newLogin)
    {
        Login=newLogin;
    }

}
