package login;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public class ScreenController {
    private static HashMap<String, Pane> screenMap = new HashMap<>();
    private static Scene main;
    private static Stage stage;

    public ScreenController(Scene main, Stage stage) {
        this.main = main;
        this.stage = stage;
    }

    public static void AddScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    public static void Activate(String name, String title, int width, int height) {

        stage.close();
        stage = new Stage();
        stage.setWidth(width);

        stage.setHeight(height);
        //main = new Scene(screenMap.get(name), width, height);
        main.setRoot(screenMap.get(name));
        stage.setTitle(title);
        stage.setScene(main);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}
