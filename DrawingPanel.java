import java.io.File;
import java.util.ArrayList;
import java.util.List;

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


/**
 * Panel odpowiedzialny za rysowanie oraz edycję figur.
 *
 * Obsługuje:
 * - rysowanie figur,
 * - zaznaczanie,
 * - przesuwanie,
 * - obracanie,
 * - skalowanie,
 * - zmianę koloru,
 * - usuwanie.
 */
public class DrawingPanel extends Pane {
    /** Aktualnie rysowana lub zaznaczona figura */
    private Shape currentFigure = null;
    /** Aktualnie otwarte menu kontekstowe */
    private ContextMenu currentMenu = null;
    /** Liczba pozostałych wierzchołków wielokąta */
    private int verLeft = 0;
    /** Całkowita liczba wierzchołków wielokąta */
    private int ver = 0;
    /** Punkt początkowy rysowania */
    private double startX = 0;
    private double startY = 0;
    /** Aktualny tryb pracy */
    private ToolMode currentMode = ToolMode.DRAW_CIRCLE;
    /** Ostatnia pozycja myszy (do przesuwania) */
    private double lastMouseX;
    private double lastMouseY;
    /**
     * Tryby działania panelu rysowania.
     */
    public enum ToolMode {
        DRAW_CIRCLE,
        DRAW_RECTANGLE,
        DRAW_POLYGON,
        EDIT,
        DELETE
    }
    /**
     * Konstruktor panelu.
     * Rejestruje obsługę myszy i wczytuje zapisane figury.
     */
    public DrawingPanel() {
        MouseEvents();
    }
    /**
     * Zmienia aktualny tryb pracy aplikacji.
     *
     * @param mode nowy tryb
     */
    public void setCurrentMode(ToolMode mode) {
        this.currentMode = mode;
        // usunięcie zaznaczenia poprzedniej figury
        if (currentFigure != null) {
            currentFigure.setStroke(Color.BLACK);
            currentFigure = null;
        }
    }
    /**
     * Wczytuje figury zapisane wcześniej w pliku.
     */
    public void loadFile(File file) {
        List<FigureData> savedData = DataManager.load(file);
        if (savedData == null) return;
        this.getChildren().clear(); 
        this.currentFigure = null;
        for (FigureData data : savedData) {
            Shape shape = null;
            switch (data.type) {
                case "RECTANGLE":
                    shape = new Rectangle(
                        data.x, data.y,
                        data.width, data.height);
                    break;
                case "CIRCLE":
                    shape = new Circle(
                        data.x, data.y, data.radius);
                    break;
                case "POLYGON":
                    Polygon p = new Polygon();
                    p.getPoints().addAll(data.points);
                    shape = p;
                    break;
            }
            // odtworzenie zapisanych parametrów figury
            if (shape != null) {
                shape.setFill(Color.valueOf(data.colorHex));
                shape.setRotate(data.rotate);
                shape.setScaleX(data.scaleX);
                shape.setScaleY(data.scaleY);
                shape.setTranslateX(data.translateX);
                shape.setTranslateY(data.translateY);
                shape.setStroke(Color.BLACK);
                this.getChildren().add(shape);
            }
        }
    }
    /**
     * Zapis wszystkich figur znajdujących się
     * aktualnie na panelu.
     */
    public void saveFile(File file) {
        List<FigureData> list = new ArrayList<>();
        for (var node : this.getChildren()) {
            if (node instanceof Shape) {
                Shape s = (Shape) node;
                FigureData fd = new FigureData();
                fd.rotate = s.getRotate();
                fd.scaleX = s.getScaleX();
                fd.scaleY = s.getScaleY();
                fd.translateX = s.getTranslateX();
                fd.translateY = s.getTranslateY();
                fd.colorHex = s.getFill().toString();
                if (s instanceof Rectangle) {
                    Rectangle r = (Rectangle) s;
                    fd.type = "RECTANGLE";
                    fd.x = r.getX();
                    fd.y = r.getY();
                    fd.width = r.getWidth();
                    fd.height = r.getHeight();
                }
                else if (s instanceof Circle) {
                    Circle c = (Circle) s;
                    fd.type = "CIRCLE";
                    fd.x = c.getCenterX();
                    fd.y = c.getCenterY();
                    fd.radius = c.getRadius();
                }
                else if (s instanceof Polygon) {
                    Polygon p = (Polygon) s;
                    fd.type = "POLYGON";
                    fd.points.addAll(p.getPoints());
                }
                list.add(fd);
            }
        }
        DataManager.save(list, file);
    }
    /**
     * Rejestruje wszystkie zdarzenia myszy.
     * Metoda odpowiada za pełną interakcję użytkownika z figurami.
     */
    private void MouseEvents() {
        /**
         * Obsługa kliknięcia myszy.
         */
        setOnMousePressed(e -> {
            switch (currentMode) {
                case DRAW_CIRCLE:
                    startX = e.getX();
                    startY = e.getY();
                    currentFigure = new Circle(startX, startY, 0);
                    currentFigure.setStroke(Color.BLACK);
                    currentFigure.setFill(Color.TRANSPARENT);
                    getChildren().add(currentFigure);
                    break;
                case DRAW_RECTANGLE:
                    startX = e.getX();
                    startY = e.getY();
                    currentFigure = new Rectangle(startX, startY, 0, 0);
                    currentFigure.setStroke(Color.BLACK);
                    currentFigure.setFill(Color.TRANSPARENT);
                    getChildren().add(currentFigure);
                    break;
                case DRAW_POLYGON:
                    if (currentFigure == null) {
                        TextInputDialog dialog = new TextInputDialog("3");
                        dialog.setTitle("Wielokąt");
                        dialog.setHeaderText("Rysowanie wielokąta.");
                        dialog.setContentText("Ile wierzchołków chcesz narysować?");
                        dialog.showAndWait().ifPresent(res -> {
                            try {
                                ver = Integer.parseInt(res);
                                if (ver < 3) {
                                    System.out.println("Min. 3 wierzchołki");
                                    return;
                                }
                                currentFigure = new Polygon();
                                currentFigure.setStroke(Color.BLACK);
                                currentFigure.setFill(Color.TRANSPARENT);
                                ((Polygon) currentFigure).getPoints().addAll(e.getX(), e.getY());
                                getChildren().add(currentFigure);
                                verLeft = ver - 1;
                            } catch (NumberFormatException ex) {
                                System.out.println(
                                    "Niepoprawna liczba");
                            }
                        });
                    }
                    else {
                        ((Polygon) currentFigure)
                            .getPoints()
                            .addAll(e.getX(), e.getY());

                        verLeft--;

                        if (verLeft <= 0)
                            currentFigure = null;
                    }
                    break;
                case EDIT:
                    if (e.getTarget() instanceof Shape) {
                        if (currentFigure != null) {
                            currentFigure.setStroke(Color.BLACK);
                        }
                        currentFigure = (Shape) e.getTarget();
                        currentFigure.setStroke(Color.RED);
                    }
                    else {
                        if (currentFigure != null) {
                            currentFigure.setStroke(Color.BLACK);
                            currentFigure = null;
                        }
                    }
                    break;
                case DELETE:
                    if (e.getTarget() instanceof Shape) {
                        getChildren().remove((Shape) e.getTarget());
                        currentFigure = null;
                    }
                    break;
            }
        });
        /**
         * Obsługa przeciągania myszy.
         */
        setOnMouseDragged(e -> {
            if (currentMode == ToolMode.EDIT && currentFigure != null) {
                if (e.getButton() == MouseButton.PRIMARY) {
                    // SHIFT -> obrót figury
                    if (e.isShiftDown()) {
                        var bounds = currentFigure.getBoundsInParent();
                        double centerX =  bounds.getMinX() + bounds.getWidth()/2;
                        double centerY = bounds.getMinY() + bounds.getHeight()/2;
                        double angle = Math.toDegrees(Math.atan2(e.getY() - centerY,e.getX() - centerX));
                        currentFigure.setRotate(angle);
                    }
                    // normalne przeciąganie -> przesuwanie
                    else {
                        double deltaX = e.getX() - lastMouseX;
                        double deltaY = e.getY() - lastMouseY;
                        currentFigure.setTranslateX(currentFigure.getTranslateX() + deltaX);
                        currentFigure.setTranslateY(currentFigure.getTranslateY() + deltaY);
                        lastMouseX = e.getX();
                        lastMouseY = e.getY();
                    }
                }
            }
            // aktualizacja prostokąta podczas rysowania
            if (currentMode == ToolMode.DRAW_RECTANGLE && currentFigure instanceof Rectangle) {
                Rectangle r = (Rectangle) currentFigure;
                double currentX = e.getX();
                double currentY = e.getY();
                r.setX(Math.min(startX, currentX));
                r.setY(Math.min(startY, currentY));
                r.setWidth(Math.abs(currentX - startX));
                r.setHeight(Math.abs(currentY - startY));
            }
            // aktualizacja promienia koła
            if (currentMode == ToolMode.DRAW_CIRCLE && currentFigure instanceof Circle) {
                Circle c = (Circle) currentFigure;
                double dx = e.getX() - startX;
                double dy = e.getY() - startY;
                c.setRadius(Math.sqrt(dx * dx + dy * dy));
            }
        });
        /**
         * Scroll – skalowanie figury.
         */
        setOnScroll(e -> {
            if (currentMode == ToolMode.EDIT && currentFigure != null) {
                double zoom = e.getDeltaY() < 0 ? 0.95 : 1.05;
                currentFigure.setScaleX(currentFigure.getScaleX() * zoom);
                currentFigure.setScaleY(currentFigure.getScaleY() * zoom);
            }
        });
        /**
         * Menu zmiany koloru (PPM).
         */
        setOnContextMenuRequested(e -> {
            if (currentMode == ToolMode.EDIT && currentFigure != null) {
                if (currentMenu != null && currentMenu.isShowing())
                    currentMenu.hide();
                currentMenu = new ContextMenu();
                ColorPicker picker = new ColorPicker((Color) currentFigure.getFill());
                picker.setOnAction(ev -> currentFigure.setFill(picker.getValue()));
                CustomMenuItem item = new CustomMenuItem(picker);
                item.setHideOnClick(false);
                currentMenu.getItems().add(item);
                currentMenu.show(
                    currentFigure,
                    e.getScreenX(),
                    e.getScreenY());
            }
        });
    }
}