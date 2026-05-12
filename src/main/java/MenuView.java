import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuView extends StackPane {
    
    class InfoDialog extends Alert {
        public InfoDialog() {
            super(AlertType.INFORMATION);
            this.setTitle("O programie");
            this.setHeaderText("Aplikacja do rysowania figur");
            this.setContentText("Autor: Szymon Banasiak\n" + "Przeznaczenie: Projekt na studia.\n");
        }
        public void Help() {
            this.setTitle("Instrukcja obsługi");
            this.setHeaderText("Aplikacja do rysowania figur");
            this.setContentText("Na górze ekranu znajduje się panel nawigacji z możliwością dodania figur i edytowania ich \n\n" +
                                "Tryb rysowania:\n" +
                                "- Lewy przycisk myszy: dodanie zaznaczonej wcześniej figury \n\n" +
                                "Tryb edytowania: \n" +
                                "- Lewy przycisk myszy: zaznaczenie figury do edycji \n" +
                                "- Przytrzymanie shifta i lewego przycisku myszy: obracanie figurą \n" +
                                "- Przytrzymanie lewego przycisku myszy: przesunięcie figury w inne miejsce \n" +
                                "- Scroll: Powiększenie/Pomniejszenie figury");
        }
    }
    public MenuView(Stage stage) {   
        VBox menu = new VBox(10);
        Button btnStart = new Button("Start");
        btnStart.setOnAction(e -> {Scene drawViewScene = new Scene(new DrawView(stage), 1280, 840); stage.setScene(drawViewScene);});
        Button btnHelp = new Button("Instrukcja");
        btnHelp.setOnAction(e -> {InfoDialog help = new InfoDialog(); help.Help(); help.showAndWait(); });
        Button btnInfo = new Button("Info");
        btnInfo.setOnAction(e -> { InfoDialog info = new InfoDialog(); info.showAndWait(); });
        menu.getChildren().addAll(btnStart, btnHelp, btnInfo);
        menu.setAlignment(Pos.CENTER);
        this.getChildren().add(menu); 
    }
}