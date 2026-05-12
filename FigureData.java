import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa przechowująca dane pojedynczej figury.
 * 
 * Nie zapisujemy obiektów JavaFX bezpośrednio,
 * tylko ich parametry potrzebne do odtworzenia.
 */
public class FigureData implements Serializable {
    private static final long serialVersionUID = 1L;
    /** Typ figury */
    public String type;
    /** Parametry geometryczne */
    public double x, y, width, height, radius;
    /** Transformacje figury */
    public double rotate, scaleX, scaleY;
    public double translateX, translateY;
    /** Kolor zapisany jako tekst HEX */
    public String colorHex;
    /** Punkty wielokąta */
    public List<Double> points = new ArrayList<>();
    public FigureData() {}
}