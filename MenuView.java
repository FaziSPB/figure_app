import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Widok menu głównego aplikacji.
 * 
 * Odpowiada za uruchomienie trybu rysowania
 * oraz wyświetlanie informacji o programie
 * i instrukcji obsługi.
 */
public class MenuView extends StackPane {
    /**
     * Okno dialogowe wykorzystywane do
     * wyświetlania informacji oraz pomocy.
     */
    class InfoDialog extends Alert {
        /**
         * Tworzy okno informacji o programie.
         */
        public InfoDialog() {
            super(AlertType.INFORMATION);
            this.setTitle("O programie");
            this.setHeaderText("Aplikacja do rysowania figur");
            this.setContentText(
                "Autor: Szymon Banasiak\n" +
                "Przeznaczenie: Projekt na studia.\n"
            );
        }
        /**
         * Zmienia zawartość dialogu na instrukcję obsługi.
         */
        public void Help() {
            this.setTitle("Instrukcja obsługi");
            this.setHeaderText("Aplikacja do rysowania figur");
            this.setContentText(
                "Na górze ekranu znajduje się panel nawigacji.\n\n" +
                "Tryb rysowania:\n" +
                "- Lewy przycisk myszy: dodanie figury\n\n" +
                "Tryb edycji:\n" +
                "- Kliknięcie: wybór figury\n" +
                "- Shift + LPM: obrót figury\n" +
                "- Przeciąganie: przesuwanie\n" +
                "- Scroll: skalowanie"
            );
        }
    }
    /**
     * Konstruktor menu głównego.
     *
     * @param stage główne okno aplikacji
     */
    public MenuView(Stage stage) {
        VBox menu = new VBox(10);
        Button btnStart = new Button("Start");
        btnStart.setOnAction(e -> {
            Scene drawViewScene =
                new Scene(new DrawView(stage), 1280, 840);
            stage.setScene(drawViewScene);
        });
        Button btnHelp = new Button("Instrukcja");
        btnHelp.setOnAction(e -> {
            InfoDialog help = new InfoDialog();
            help.Help();
            help.showAndWait();
        });
        Button btnInfo = new Button("Info");
        btnInfo.setOnAction(e -> {
            InfoDialog info = new InfoDialog();
            info.showAndWait();
        });
        menu.getChildren().addAll(btnStart, btnHelp, btnInfo);
        menu.setAlignment(Pos.CENTER);
        this.getChildren().add(menu);
    }
}