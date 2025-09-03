import Commands.Command;
import Commands.HelpCommand;
import Common.CommandResponse;
import Data.Flat;
import Managers.CollectionManager;
import Managers.CommandManager;
import Managers.FileManager;
import Managers.InputHelper0;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class playground {
    public static void main(String[] args) throws IOException {
        FileManager fmanager = new FileManager();
        PriorityQueue<Flat> flats = fmanager.readFromCsv("C:\\Users\\rutma\\OneDrive\\Рабочий стол\\Test1.csv");
        System.out.println(flats);
    }
}
