package Garbage;

import Data.Flat;
import Managers.FileManager;

import java.io.IOException;
import java.util.PriorityQueue;

public class playground {
    public static void main(String[] args) throws IOException {
        FileManager fmanager = new FileManager();
        PriorityQueue<Flat> flats = fmanager.readFromCsv("C:\\Users\\rutma\\OneDrive\\Рабочий стол\\Test1.csv");
        System.out.println(flats);
    }
}
