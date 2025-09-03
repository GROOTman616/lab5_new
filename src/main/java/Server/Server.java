package Server;

import Commands.Command;
import Commands.SaveCommand;
import Common.CommandRequest;
import Common.CommandResponse;
import Managers.CollectionManager;
import Managers.CommandManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Server {
    private final int port;
    private String filename;
    private final Scanner sc = new Scanner(System.in);
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    //хардкод убрать

    public Server(int port, String filename) throws IOException {
        this.filename = filename;
        this.port = port;
        this.collectionManager = new CollectionManager(filename);
        this.commandManager = new CommandManager(collectionManager);
    }
    public void run() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port);

            while (true) {
                try (Socket client = acceptClient(serverSocket)) {
                    handleClient(client);
                } catch (IOException e) {
                    System.out.println("Ошибка соединения с клиентом: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка сервера: " + e.getMessage());
        }
    }

    private Socket acceptClient(ServerSocket serverSocket) throws IOException {
        Socket client = serverSocket.accept();
        System.out.println("Клиент подключился: " + client.getInetAddress());
        return client;
    }

    private void handleClient(Socket client) {
        try (InputStream in = client.getInputStream();
        OutputStream out = client.getOutputStream()) {
            while (true) {
                CommandRequest request = readRequest(in);
                if (request == null) {
                    System.out.println("Клиент отключился");
                    save();
                    break;
                }
                CommandResponse response = processRequest(request);
                sendResponse(out, response);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при обработке клиента: " + e.getMessage());
        }
    }

    private CommandRequest readRequest(InputStream in) {
        try {
            byte[] lenBytes = in.readNBytes(4);
            if (lenBytes.length < 4) {
                return null;
            }
            int length = ByteBuffer.wrap(lenBytes).getInt();
            byte[] data = in.readNBytes(length);
            if (data.length < length) {
                return null;
            }
            try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(data))) {
                return (CommandRequest) objIn.readObject();
            }
        } catch (Exception e) {
            System.out.println("Ошибка при чтении запроса: " + e.getMessage());
            return null;
        }
    }

    private CommandResponse processRequest(CommandRequest request) throws IOException {
        String commandName = request.getCommandName();
        Object[] commandArgs = request.getArgs();
        Object dataObj = request.getData();
        Command command = commandManager.getCommands().get(commandName);

        if (command==null) {
            return new CommandResponse(false, "Неизвестная команда: " + commandName);
        } else {
            return command.execute(commandArgs, dataObj, sc);
        }
    }

    private void sendResponse(OutputStream out, CommandResponse response) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            try (ObjectOutputStream objOut = new ObjectOutputStream(byteOut)) {
                objOut.writeObject(response);
                objOut.flush();
            }
            byte[] respData = byteOut.toByteArray();
            ByteBuffer buffer = ByteBuffer.allocate(4+respData.length);
            buffer.putInt(respData.length);
            buffer.put(respData);
            out.write(buffer.array());
            out.flush();
        } catch (IOException e) {
            System.out.println("Ошибка при отправке ответа: " + e.getMessage());
        }
    }
    private void save() throws IOException {
        SaveCommand command = new SaveCommand(collectionManager);
        CommandResponse response = command.execute(null, null, sc);
    }
}
