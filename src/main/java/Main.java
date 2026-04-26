import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new MenuView(stage), 800, 600));
        stage.setTitle("Figury");
        stage.show();
        stage.sizeToScene();
    }

    public static void main(String[] args) {
        launch();
    }
}
