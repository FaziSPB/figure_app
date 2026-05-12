import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DrawView extends BorderPane {
    private DrawingPanel drawingPanel;
    
    public DrawView(Stage stage) {
        drawingPanel = new DrawingPanel();
        setCenter(drawingPanel);
        HBox navbar = new HBox(10);
        Button btnCircle = new Button("Okrag");
        btnCircle.setOnAction(e -> { drawingPanel.setCurrentMode(DrawingPanel.ToolMode.DRAW_CIRCLE); });
        Button btnRect = new Button("Prostokąt");
        btnRect.setOnAction(e -> drawingPanel.setCurrentMode(DrawingPanel.ToolMode.DRAW_RECTANGLE));
        Button btnPoly = new Button("Wielokąt");
        btnPoly.setOnAction(e -> drawingPanel.setCurrentMode(DrawingPanel.ToolMode.DRAW_POLYGON));
        Button btnEdit = new Button("Edytuj figurę");
        btnEdit.setOnAction(e -> drawingPanel.setCurrentMode(DrawingPanel.ToolMode.EDIT));
        Button btnDelete = new Button("Usuń figurę");
        btnDelete.setOnAction(e -> drawingPanel.setCurrentMode(DrawingPanel.ToolMode.DELETE));
        Button btnMenu = new Button("Wróć do menu");
        btnMenu.setOnAction(e -> {Scene MenuView = new Scene(new MenuView(stage), 1280, 840); stage.setScene(MenuView);});
        navbar.getChildren().addAll(btnCircle, btnRect, btnPoly, btnEdit, btnDelete);
        navbar.setAlignment(Pos.CENTER);
        setTop(navbar);
    }
    public DrawingPanel getDrawingPanel() {
        return drawingPanel; 
    }
}
