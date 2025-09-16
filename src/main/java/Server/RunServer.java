package Server;

import DataBase.DBConnector;
import DataBase.DBManager;

import java.sql.Connection;
import java.util.Scanner;

public class RunServer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите порт: ");
        int port = Integer.parseInt(sc.nextLine().trim());

        System.out.println("Введите URL БД: ");
        String url = sc.nextLine().trim();

        System.out.println("Введите пользователя БД: ");
        String user = sc.nextLine().trim();

        System.out.println("Введите пароль БД: ");
        String password = sc.nextLine().trim();
        try {
            DBConnector connector = new DBConnector(url, user, password);
            Connection connection = connector.getConnection();
            DBManager dbManager = new DBManager(connection);

            Server server = new Server(port, dbManager);
            server.run();
        } catch (Exception e) {
            System.out.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }
}
