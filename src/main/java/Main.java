import Managers.CollectionManager;
import Managers.CommandManager;
import Managers.InputHelper;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Здравствуйте!");
        System.out.print("Введите название файла: ");
        String filename = sc.nextLine();
        CollectionManager colmanager = new CollectionManager(filename);
        InputHelper inputHelper = new InputHelper();
        CommandManager commandManager = new CommandManager(colmanager, inputHelper);
        while (true) {
            System.out.print("Введите команду: ");
            String answer = sc.nextLine();
            commandManager.executeCommand(answer, sc);
        }
    }
}