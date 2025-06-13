package Managers;

import Commands.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class CommandManager {
    private final HashMap<String, Command> commands = new HashMap<>();
    private final CollectionManager collectionManager;
    private final InputHelper inputManager;

    public CommandManager(CollectionManager collectionManager, InputHelper inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
        registerCommands();
    }

    private void registerCommands() {
        commands.put("help", new HelpCommand(this));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("add", new AddCommand(collectionManager));
        commands.put("update", new UpdateIdCommand(collectionManager));
        commands.put("remove_by_id", new RemoveIdCommand(collectionManager));
        commands.put("clear", new ClearCommand(collectionManager));
        commands.put("save", new SaveCommand(collectionManager));
        commands.put("execute_script", new ExecuteScriptCommand(this));
        commands.put("exit", new ExitCommand());
        commands.put("add_if_min", new AddIfMinCommand(collectionManager));
        commands.put("add_if_max", new AddIfMaxCommand(collectionManager));
        commands.put("remove_head", new RemoveHeadCommand(collectionManager));
        commands.put("remove_all_by_number_of rooms", new RemoveByNumberOfRoomsCommand(collectionManager));
        commands.put("filter_greater_than_price", new FilterGreaterThanPriceCommand(collectionManager));
        commands.put("print_field_ascending_transport", new PrintFieldAscendingTransportCommand(collectionManager));
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public void executeCommand(String input, Scanner scanner) throws IOException {
        String[] parts = input.trim().split(" ");
        String commandName = parts[0];
        Command command = commands.get(commandName);
        if (command!=null){
            command.execute(parts, scanner);
        } else {
            System.err.println("Неизвестная команда: " + commandName);
        }
    }
}
