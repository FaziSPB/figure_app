import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
/**
 * Główny widok rysowania.
 * 
 * Zawiera panel rysowania oraz pasek narzędzi
 * pozwalający zmieniać tryb pracy aplikacji.
 */
public class DrawView extends BorderPane {
    private DrawingPanel drawingPanel;
    /**
     * Tworzy widok rysowania.
     *
     * @param stage główne okno aplikacji
     */
    public DrawView(Stage stage) {
        drawingPanel = new DrawingPanel();
        setCenter(drawingPanel);
        HBox navbar = new HBox(10);
        Button btnCircle = new Button("Okrag");
        btnCircle.setOnAction(e ->
            drawingPanel.setCurrentMode(
                DrawingPanel.ToolMode.DRAW_CIRCLE));
        Button btnRect = new Button("Prostokąt");
        btnRect.setOnAction(e ->
            drawingPanel.setCurrentMode(
                DrawingPanel.ToolMode.DRAW_RECTANGLE));
        Button btnPoly = new Button("Wielokąt");
        btnPoly.setOnAction(e ->
            drawingPanel.setCurrentMode(
                DrawingPanel.ToolMode.DRAW_POLYGON));
        Button btnEdit = new Button("Edytuj figurę");
        btnEdit.setOnAction(e ->
            drawingPanel.setCurrentMode(
                DrawingPanel.ToolMode.EDIT));
        Button btnDelete = new Button("Usuń figurę");
        btnDelete.setOnAction(e ->
            drawingPanel.setCurrentMode(
                DrawingPanel.ToolMode.DELETE));
        Button btnMenu = new Button("Wróć do menu");
        btnMenu.setOnAction(e ->
            stage.setScene(new Scene(new MenuView(stage), 1280, 840)));
        navbar.getChildren().addAll(
            btnCircle, btnRect, btnPoly, btnEdit, btnDelete);
        navbar.setAlignment(Pos.CENTER);
        setTop(navbar);
    }
    /**
     * Zwraca panel rysowania.
     *
     * @return DrawingPanel
     */
    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }
}