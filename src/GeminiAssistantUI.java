import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.MainUI;

public class GeminiAssistantUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainUI mainUI = new MainUI();
        Scene scene = new Scene(mainUI.createContent(primaryStage), 800, 600);
        primaryStage.setTitle("AI Resume & Email Assistant");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
