package Client;

import java.io.IOException;
import java.util.Scanner;

public class RunClient {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите порт: ");
        int port = Integer.parseInt(sc.nextLine().trim());
        Client client = new Client(port);
        client.run();
    }
}
