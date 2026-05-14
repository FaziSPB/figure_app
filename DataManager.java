import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
/**
 * Klasa odpowiedzialna za zapis oraz odczyt danych figur.
 * 
 * Dane zapisywane są w pliku binarnym przy użyciu serializacji.
 */
public class DataManager {
    /**
     * Zapisuje listę figur do pliku.
     *
     * @param data lista danych figur
     */
    public static void save(List<FigureData> data, File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
            System.out.println("Zapisano");
        } catch (IOException e) {
            System.err.println("Błąd" + e.getMessage());
        }
    }
    /**
     * Wczytuje zapisane figury z pliku.
     *
     * @return lista figur lub null gdy brak pliku
     */
    @SuppressWarnings("unchecked")
    public static List<FigureData> load(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<FigureData>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Brak zapisanych danych.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd" + e.getMessage());
        }
        return null;
    }
}