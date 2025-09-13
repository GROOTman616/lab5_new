package Client;

import Common.CommandRequest;
import Common.CommandResponse;
import Common.User;
import Managers.CommandManager;
import Managers.InputHelper0;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Client {
    Scanner scanner = new Scanner(System.in);
    InputHelper0 inputHelper0 = new InputHelper0(scanner);
    CommandManager commandManager = new CommandManager();
    int port;
    private ByteBuffer buffer = ByteBuffer.allocate(8);
    private final Set<String> activeScripts = new HashSet<>();
    private User user;

    public Client(int port) {
        this.port = port;
    }

    public void run() {
        String host = "localhost";

        System.out.print("Введите логин: ");
        String login = scanner.nextLine().trim();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine().trim();
        user = new User (login, password);
        while (true) {
            try (SocketChannel socketChannel = SocketChannel.open()) {
                socketChannel.connect(new InetSocketAddress(host, port));
                while (!socketChannel.finishConnect()) {

                }
                System.out.println("Подключение установлено с " + host + ":" + port);

                while (true) {
                    System.out.print("Введите команду: ");
                    String input = scanner.nextLine().trim();
                    String[] parts = input.split(" ");
                    String commandName = parts[0];
                    String[] commandArgs = new String[parts.length - 1];
                    System.arraycopy(parts, 1, commandArgs, 0, commandArgs.length);

                    try {
                        if (commandName.equals("exit")) {
                            System.out.println("Завершение программы . . .");
                            System.exit(0);
                        }
                        if (commandName.equals("execute_script")) {
                            execute_script(commandArgs[0], socketChannel);
                        } else {
                            CommandRequest request;
                            if (commandManager.getCommandsWithFlat().containsKey(commandName)) {
                                Object flat = inputHelper0.readFlat();
                                request = new CommandRequest(commandName, commandArgs, flat, user);
                            } else {
                                request = new CommandRequest(commandName, commandArgs, null, user);
                            }
                            sendRequest(socketChannel, request);

                            CommandResponse response = readResponse(socketChannel);
                            System.out.println("\n--- Ответ сервера ---");
                            System.out.println(response.getMessage());
                            System.out.println("----------------------\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка при отправке команды: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.out.println("Ошибка при соединении с сервером: " + e.getMessage());
                System.out.println("Не удалось подключиться к серверу. Повторная попытка через 3 секунды...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    public void execute_script(String filename, SocketChannel socketChannel) {
        if (activeScripts.contains(filename)) {
            System.out.println("Ошибка: рекурсивный вызов скрипта " + filename + " запрещён.");
            return;
        }

        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Ошибка: файл " + filename + " не найден.");
            return;
        }

        try (Scanner scriptscanner = new Scanner(file)) {
            activeScripts.add(filename);
            while (scriptscanner.hasNextLine()) {
                String line = scriptscanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(" ");
                String commandName = parts[0];
                String[] commandArgs = new String[parts.length - 1];
                System.arraycopy(parts, 1, commandArgs, 0, commandArgs.length);

                if (commandName.equals("execute_script")) {
                    if (commandArgs.length == 0) {
                        System.out.println("Ошибка: не указан файл для execute_script");
                        continue;
                    }
                    execute_script(commandArgs[0], socketChannel);
                } else {
                    try {
                        CommandRequest request;
                        if (commandManager.getCommandsWithFlat().containsKey(commandName)) {
                            Object flat = inputHelper0.readFlat();
                            request = new CommandRequest(commandName, commandArgs, flat, user);
                        } else {
                            request = new CommandRequest(commandName, commandArgs, null, user);
                        }

                        sendRequest(socketChannel, request);
                        CommandResponse response = readResponse(socketChannel);

                        System.out.println("\n--- Ответ сервера ---");
                        System.out.println(response.getMessage());
                        System.out.println("----------------------\n");
                    } catch (Exception e) {
                        System.out.println("Ошибка при обработке команды из скрипта: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла " + filename + ": " + e.getMessage());
        } finally {
            activeScripts.remove(filename);
        }
    }

    private void sendRequest(SocketChannel channel, CommandRequest request) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(request);
        objOut.flush();

        byte[] data = byteOut.toByteArray();
        ByteBuffer buffer = ByteBuffer.allocate(4 + data.length);
        buffer.putInt(data.length);
        buffer.put(data);
        buffer.flip();
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        System.out.println("Запрос отправлен серверу");
    }

    private CommandResponse readResponse(SocketChannel channel) throws IOException, ClassNotFoundException {
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        while (lengthBuffer.hasRemaining()) {
            if (channel.read(lengthBuffer) == -1) throw new EOFException("Сервер закрыл соединение");
        }
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        if(buffer.capacity() < length) {
            buffer = ByteBuffer.allocate(length);
        } else {
            buffer.clear();
        }

        buffer.limit(length);
        while (buffer.hasRemaining()) {
            if (channel.read(buffer) == -1) throw new EOFException("Сервер закрыл соединение");
        }
        byte[] data = new byte[length];
        buffer.flip();
        buffer.get(data);

        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(data))) {
            CommandResponse response = (CommandResponse) objIn.readObject();
            System.out.println("Получен ответ от сервера");
            return response;
        }
    }
}
