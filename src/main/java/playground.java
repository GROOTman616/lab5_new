import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class playground {
    public static void main(String[] args) throws IOException {
        PriorityQueue<Flat> flats = new PriorityQueue<>();
        CollectionManager collectionManager = new CollectionManager();
        flats.add(InputHelper.readFlat());
        flats.add(InputHelper.readFlat());
        FileManager.writeToCsv(collectionManager.filename, flats);
    }
}
