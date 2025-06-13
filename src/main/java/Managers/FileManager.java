import java.io.*;
import java.util.*;

public class FileManager {
    public PriorityQueue<Flat> readFromCsv(String fileName) throws FileNotFoundException {
        PriorityQueue<Flat> flats = new PriorityQueue<>();
        FileReader fr = new FileReader(fileName);
        try {
            String wholefile = "";
            int temp;
            while ((temp = fr.read()) != -1) {
                wholefile += (char) temp;
            }
            String[] lines = wholefile.split("\n");
            for (String flatline:lines){
                Flat flat = converter.fromCsv(flatline);
                flats.add(flat);
                fr.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            System.err.println("Пропущена строка из-за ошибки: " + e.getMessage());
        }
        return flats;
    }




    public static void writeToCsv(String fileName, PriorityQueue<Flat> flats) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName))) {
            for (Flat flat : flats) {
                String line = converter.toCsv(flat) + "\n";
                out.write(line.getBytes());
            }
        }
    }
}

