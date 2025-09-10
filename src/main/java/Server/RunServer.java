package Server;

import java.util.Scanner;

public class RunServer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите порт: ");
        int port = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Введите имя файла: ");
        String filename = sc.nextLine();
        try {
            Server2 server = new Server2(port, filename);
            server.run();
        } catch (Exception e) {
            System.out.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }
}
