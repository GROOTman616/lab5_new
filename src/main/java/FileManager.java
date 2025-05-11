import java.io.*;
import java.util.*;

public class FileManager {
    public static void readFromCsv(String fileName) throws FileNotFoundException {
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
                System.out.println(flatline);
                Flat flat = converter.fromCsv(flatline);
                flats.add(flat);
            }
            flats.forEach(System.out::println);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        try {
//            Flat flat = converter.fromCsv(line);
//            flats.add(flat);
//        } catch (IllegalArgumentException e) {
//            System.err.println("Пропущена строка из-за ошибки: " + e.getMessage());
//        }
    }




//    public static void writeToCsv(String fileName, Collection<Flat> flats) throws IOException {
//        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName))) {
//            for (Flat flat : flats) {
//                String line = FlatCsvConverter.toCsv(flat) + "\n";
//                out.write(line.getBytes());
//            }
//        }
//    }
}

