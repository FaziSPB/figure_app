import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new MenuView(stage), 1280, 840));
        stage.setTitle("Figury");
        stage.show();
        stage.sizeToScene();
        stage.setOnCloseRequest(e -> {
            if (stage.getScene().getRoot() instanceof DrawView) {
                DrawView dv = (DrawView) stage.getScene().getRoot();
                dv.getDrawingPanel().Autosave();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
