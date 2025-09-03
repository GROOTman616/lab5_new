package Managers;

import Commands.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class CommandManager implements Serializable {
    private final HashMap<String, Command> commands = new HashMap<>();
    private final HashMap<String, Command> commandsWithFlat = new HashMap<>();
    private final HashSet<String> commandNames = new HashSet<>();
    private CollectionManager collectionManager;
    private InputHelper0 inputManager;

    public CommandManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        registerCommands();
    }

    public CommandManager() {
        registerCommandNames();
    }

    private void registerCommands() {
        commands.put("help", new HelpCommand(this));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("add", new AddCommand(collectionManager, inputManager));
        commands.put("update", new UpdateIdCommand(collectionManager));
        commands.put("remove_by_id", new RemoveIdCommand(collectionManager));
        commands.put("clear", new ClearCommand(collectionManager));
//        commands.put("save", new SaveCommand(collectionManager));
        commands.put("execute_script", new ExecuteScriptCommand(this));
        commands.put("exit", new ExitCommand());
        commands.put("add_if_min", new AddIfMinCommand(collectionManager, inputManager));
        commands.put("add_if_max", new AddIfMaxCommand(collectionManager, inputManager));
        commands.put("remove_head", new RemoveHeadCommand(collectionManager));
        commands.put("remove_all_by_number_of_rooms", new RemoveByNumberOfRoomsCommand(collectionManager));
        commands.put("filter_greater_than_price", new FilterGreaterThanPriceCommand(collectionManager));
        commands.put("print_field_ascending_transport", new PrintFieldAscendingTransportCommand(collectionManager));

        commandsWithFlat.put("add", commands.get("add"));
        commandsWithFlat.put("add_if_min", commands.get("add_if_min"));
        commandsWithFlat.put("add_if_max", commands.get("add_if_max"));
        commandsWithFlat.put("update", commands.get("update"));
    }

    private void registerCommandNames() {
        commandNames.add("help");
        commandNames.add("info");
        commandNames.add("show");
        commandNames.add("add");
        commandNames.add("update");
        commandNames.add("remove_by_id");
        commandNames.add("clear");
//        commands.put("save", new SaveCommand(collectionManager));
        commandNames.add("execute_script");
        commandNames.add("exit");
        commandNames.add("add_if_min");
        commandNames.add("add_if_max");
        commandNames.add("remove_head");
        commandNames.add("remove_all_by_number_of_rooms");
        commandNames.add("filter_greater_than_price");
        commandNames.add("print_field_ascending_transport");

        commandsWithFlat.put("add", commands.get("add"));
        commandsWithFlat.put("add_if_min", commands.get("add_if_min"));
        commandsWithFlat.put("add_if_max", commands.get("add_if_max"));
        commandsWithFlat.put("update", commands.get("update"));
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
    public HashMap<String, Command> getCommandsWithFlat() {return commandsWithFlat;}

    public void executeCommand(String input, Object data, Scanner scanner) throws IOException {
        String[] parts = input.trim().split(" ");
        String commandName = parts[0];
        Command command = commands.get(commandName);
        if (command!=null){
            command.execute(parts, data, scanner);
        } else {
            System.err.println("Неизвестная команда: " + commandName);
        }
    }
}
