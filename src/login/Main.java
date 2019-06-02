package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("logowanie.fxml"));
        primaryStage.setTitle("Baza Danych Antykwariatów");
        Scene scene = new Scene(root, 290, 190);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        ScreenController screenController = new ScreenController(scene, primaryStage);
        ScreenController.addScreen("login", FXMLLoader.load(getClass().getResource("logowanie.fxml")));
        ScreenController.addScreen("admin", FXMLLoader.load(getClass().getResource("/admin/admin_menu.fxml")));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
