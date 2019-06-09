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
import pracownik.Pracownik;
import pracownik.PracownikController;
import pracownik.PracownikDAO;

import java.io.IOException;
import java.util.prefs.Preferences;

public class LogController {
    // Preferences preferences=Preferences.userNodeForPackage(LogController.class);

    private static String Login;
    private static String Password;

    @FXML
    private PasswordField password;

    @FXML
    private TextField login;


    public void Connect(ActionEvent actionEvent) throws IOException {
        String pwd = password.getText();
        String lg = login.getText();
        Login = pwd;

        if (pwd.equals("admin") && lg.equals("admin")) {

            ScreenController.Activate("admin", "Menadżer pracowników", 770, 430);
        } else if (PracownikDAO.IsInDB(lg) && lg.equals(pwd)) {
            PracownikDAO.id = PracownikDAO.GetIdFromNazwisko(lg);

            ScreenController.Activate("pracownik","Konto Pracownika",770, 430);
        }

        //gdy sie nie łaczy z baz danych
        else {
            password.clear();
            login.clear();
            password.setPromptText(Password);
            login.setPromptText(Login);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Podano złe hasło lub login");
            alert.setTitle("Bład logowania");
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