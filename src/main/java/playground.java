import Data.Flat;
import Managers.InputHelper;

import java.io.IOException;
import java.util.PriorityQueue;

public class playground {
    public static void main(String[] args) throws IOException {
        PriorityQueue<Flat> flats = new PriorityQueue<>();
//        Managers.CollectionManager collectionManager = new Managers.CollectionManager();
        flats.add(InputHelper.readFlat());
        flats.add(InputHelper.readFlat());
//        Managers.FileManager.writeToCsv(collectionManager.filename, flats);
    }
}
