import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Główna klasa aplikacji JavaFX.
 * 
 * Otwiera okno programu oraz obsługuje
 * zapis danych przy zamykaniu aplikacji.
 */
public class Main extends Application {
    /**
     * Metoda startowa JavaFX.
     *
     * @param stage główne okno aplikacji
     */
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new MenuView(stage), 1280, 840));
        stage.setTitle("Figury");
        stage.show();
        // Automatyczny zapis figur przy zamykaniu programu
        stage.setOnCloseRequest(e -> {
            if (stage.getScene().getRoot() instanceof DrawView) {
                DrawView dv = (DrawView) stage.getScene().getRoot();
                dv.getDrawingPanel().Autosave();
            }
        });
    }
    /**
     * Uruchamia aplikację.
     */
    public static void main(String[] args) {
        launch();
    }
}