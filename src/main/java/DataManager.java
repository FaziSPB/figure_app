import java.io.*;
import java.util.List;

public class DataManager {
    private static final String FILE_NAME = "figures.dat";
    public static void save(List<FigureData> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(data);
            System.out.println("Zapisano");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    public static List<FigureData> load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<FigureData>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Brak.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}