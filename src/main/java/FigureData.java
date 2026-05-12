import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FigureData implements Serializable {
    private static final long serialVersionUID = 1L; 
    public String type;
    public double x, y, width, height, radius;
    public double rotate, scaleX, scaleY, translateX, translateY;
    public String colorHex; 
    public List<Double> points = new ArrayList<>();
    public FigureData() {}
}
