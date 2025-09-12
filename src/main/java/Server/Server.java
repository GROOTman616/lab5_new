package Server;

import Commands.Command;
import Commands.SaveCommand;
import Common.CommandRequest;
import Common.CommandResponse;
import Common.User;
import DataBase.DBManager;
import Managers.CollectionManager;
import Managers.CommandManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    private final DBManager dbManager;
    private final int port;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;

    private final ForkJoinPool readPool = new ForkJoinPool();
    private final ExecutorService processPool = Executors.newCachedThreadPool();

    public Server(int port) throws IOException {
        this.port = port;
        this.dbManager= new DBManager("jdbc:postgresql://localhost:5432/studs", "user", "password");
        this.collectionManager = new CollectionManager(dbManager.loadFlats());
    }

    public void run() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Сервер запущен на порту " + port);

        while (true) {
            selector.select();
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                if (key.isAcceptable()) {
                    acceptClient(serverChannel, selector);
                } else if (key.isReadable()) {
                    readPool.submit(() -> handleClientRequest(key));
                }
            }
        }
    }

    private void acceptClient(ServerSocketChannel serverChannel, Selector selector) throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(4096));
        System.out.println("Клиент подключился: " + clientChannel.getRemoteAddress());
    }

    private void handleClientRequest(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        try {
            int bytesRead = clientChannel.read(buffer);
            if (bytesRead == -1) {
                System.out.println("Клиент отключился: " + clientChannel.getRemoteAddress());
                save();
                clientChannel.close();
                return;
            }

            buffer.flip();
            if (buffer.remaining() < 4) {
                buffer.compact();
                return;
            }

            buffer.mark();
            int length = buffer.getInt();

            if (buffer.remaining() < length) {
                buffer.reset();
                buffer.compact();
                return;
            }

            byte[] data = new byte[length];
            buffer.get(data);
            buffer.compact();

            CommandRequest request;
            try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(data))) {
                request = (CommandRequest) objIn.readObject();
            }

            processPool.submit(() -> {
                try {
                    CommandResponse response = processRequest(request);
                    new Thread(() -> sendResponse(clientChannel, response)).start();
                } catch (Exception e) {
                    System.out.println("Ошибка обработки команды: " + e.getMessage());
                }
            });

        } catch (Exception e) {
            try {
                System.out.println("Ошибка при обработке клиента: " + e.getMessage());
                clientChannel.close();
            } catch (IOException ignored) {}
        }
    }

    private CommandResponse processRequest(CommandRequest request) throws IOException {
        User user = request.getUser();
        if (user == null || !(user)) {
            return new CommandResponse(false, "Ошибка авторизации. Проверьте логин и пароль.");
        }

        String commandName = request.getCommandName();
        Object[] commandArgs = request.getArgs();
        Object dataObj = request.getData();

        Command command = commandManager.getCommands().get(commandName);

        if (command == null) {
            return new CommandResponse(false, "Неизвестная команда: " + commandName);
        }
        return command.execute(commandArgs, dataObj, user);
    }

    private void sendResponse(SocketChannel clientChannel, CommandResponse response) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            try (ObjectOutputStream objOut = new ObjectOutputStream(byteOut)) {
                objOut.writeObject(response);
                objOut.flush();
            }
            byte[] respData = byteOut.toByteArray();

            ByteBuffer outBuffer = ByteBuffer.allocate(4 + respData.length);
            outBuffer.putInt(respData.length);
            outBuffer.put(respData);
            outBuffer.flip();

            clientChannel.write(outBuffer);
        } catch (IOException e) {
            System.out.println("Ошибка при отправке ответа клиенту: " + e.getMessage());
        }
    }

    private void save() throws IOException {
        SaveCommand command = new SaveCommand(collectionManager);
        command.execute(null, null, null);
    }

    // Утилита для хэширования паролей
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-224");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при хэшировании пароля", e);
        }
    }
}