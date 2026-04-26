import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DrawingPanel extends Pane {
    Shape currentFigure = null;
    int verLeft = 0;
    ToolMode currentMode = ToolMode.DRAW_CIRCLE;
    double lastMouseX;
    double lastMouseY;
    public enum ToolMode {
        DRAW_CIRCLE,
        DRAW_RECTANGLE,
        DRAW_POLYGON,
        EDIT
    }
    public void setCurrentMode(ToolMode mode) {
    this.currentMode = mode;
    if (currentFigure != null) {
        currentFigure.setStroke(Color.BLACK);
        currentFigure = null;
    }
}
    public DrawingPanel() {
        MouseEvents();
    }
    public void MouseEvents() {
        setOnMousePressed(e -> {
            switch (currentMode) {
            case DRAW_CIRCLE:
                Circle circle = new Circle();
                circle.setCenterX(e.getX());
                circle.setCenterY(e.getY());
                circle.setRadius(50);
                circle.setStroke(Color.BLACK);
                circle.setFill(Color.TRANSPARENT);
                this.getChildren().add(circle);
                break;
            case DRAW_RECTANGLE: 
                Rectangle rectangle = new Rectangle();
                rectangle.setX(e.getX());
                rectangle.setY(e.getY());
                rectangle.setWidth(50);
                rectangle.setHeight(50);
                rectangle.setStroke(Color.BLACK);
                rectangle.setFill(Color.TRANSPARENT);
                this.getChildren().add(rectangle);
                break;
            case DRAW_POLYGON:
                if (currentFigure == null) {
                    TextInputDialog dialog = new TextInputDialog("3"); 
                    dialog.setTitle("Wielokąt");
                    dialog.setHeaderText("Rysowanie wielokąta.");
                    dialog.setContentText("Ile wierzchołków chcesz narysować?");
                    dialog.showAndWait().ifPresent(res -> {
                        try {
                            int ver = Integer.parseInt(res);
                            if (ver < 3) {
                                System.out.println("Wielokąt musi mieć min. 3 wierzchołki");
                                return;
                            }
                            currentFigure = new Polygon();
                            currentFigure.setStroke(Color.BLACK);
                            currentFigure.setFill(Color.TRANSPARENT);
                            ((Polygon) currentFigure).getPoints().addAll(e.getX(), e.getY());
                            this.getChildren().add(currentFigure);
                            verLeft = ver - 1;

                        } catch (NumberFormatException ex) {
                            System.out.println("Podano nieprawidłową liczbę");
                        }
                    });
                } 
                else {
                    ((Polygon) currentFigure).getPoints().addAll(e.getX(), e.getY());
                    verLeft--;
                    if (verLeft <= 0) {
                        currentFigure = null; 
                    }
                }
                break;
            case EDIT:
                if (e.getTarget() instanceof Shape) {
                    if (currentFigure != null) {
                        currentFigure.setStroke(Color.BLACK);
                    }
                    currentFigure = (Shape) e.getTarget();
                    currentFigure.setStroke(Color.RED);
                    lastMouseX = e.getX();
                    lastMouseY = e.getY();
                } else {
                    if (currentFigure != null) {
                        currentFigure.setStroke(Color.BLACK);
                        currentFigure = null;
                    }
                }
                break; 
            default:
                throw new AssertionError();
            }
        });
        setOnMouseDragged(e -> {
            if (currentMode == ToolMode.EDIT && currentFigure != null) {
                if (e.getButton() == MouseButton.PRIMARY) {
                    if (e.isShiftDown()) {
                    var bounds = currentFigure.getBoundsInParent();
                    double centerX = bounds.getMinX() + bounds.getWidth() / 2;
                    double centerY = bounds.getMinY() + bounds.getHeight() / 2;
                    double deltaX = e.getX() - centerX;
                    double deltaY = e.getY() - centerY;
                    double angleInRadians = Math.atan2(deltaY, deltaX);
                    double angleInDegrees = Math.toDegrees(angleInRadians);
                    currentFigure.setRotate(angleInDegrees);}
                    else {
                    double deltaX = e.getX() - lastMouseX;
                    double deltaY = e.getY() - lastMouseY;
                    currentFigure.setTranslateX(currentFigure.getTranslateX() + deltaX);
                    currentFigure.setTranslateY(currentFigure.getTranslateY() + deltaY);
                    lastMouseX = e.getX();
                    lastMouseY = e.getY();}
                } 
                
            }
        });
        setOnScroll(e -> {
            if (currentMode == ToolMode.EDIT && currentFigure != null) {
                double zoom = 1.05;
                if (e.getDeltaY() < 0) {
                    zoom = 0.95;
                }
                currentFigure.setScaleX(currentFigure.getScaleX() * zoom);
                currentFigure.setScaleY(currentFigure.getScaleY() * zoom);
            }
        });
        setOnContextMenuRequested(e -> {
            if (currentMode == ToolMode.EDIT && currentFigure != null) {
                ContextMenu contextMenu = new ContextMenu();
                ColorPicker colorPicker = new ColorPicker((Color) currentFigure.getFill());
                colorPicker.setOnAction(event -> {
                    currentFigure.setFill(colorPicker.getValue());
                });
                CustomMenuItem colorItem = new CustomMenuItem(colorPicker);
                colorItem.setHideOnClick(false); 
                contextMenu.getItems().add(colorItem);
                contextMenu.show(currentFigure, e.getScreenX(), e.getScreenY());
            }
        });
    }
}



