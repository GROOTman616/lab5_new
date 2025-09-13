//import Managers.CollectionManager;
////C:\Users\rutma\OneDrive\Рабочий стол\Test1.csv
////
//import Managers.CommandManager;execute_script C:\Users\rutma\test3.txt
//import Managers.InputHelper0;
//
//import java.io.IOException;
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) throws IOException {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Здравствуйте!");
//        System.out.print("Введите название файла: ");
//        String filename = sc.nextLine();
//        CollectionManager colmanager = new CollectionManager();
//        InputHelper0 inputHelper = new InputHelper0(sc);
//        CommandManager commandManager = new CommandManager(colmanager);
//        System.out.println("Справка: ");
//        commandManager.executeCommand("help", null, sc);
//        while (true) {
//            System.out.print("Введите команду: ");
//            String answer = sc.nextLine().toLowerCase();
//            commandManager.executeCommand(answer, null, sc);
//        }
//    }
//}