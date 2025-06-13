package Commands;

import Managers.CollectionManager;
import Managers.CommandManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class HelpCommand implements Command{
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager){
        this.commandManager=commandManager;
    }
    @Override
    public void execute(String[] args, Scanner scanner) throws IOException {
        HashMap<String, Command> commands = commandManager.getCommands();
        for (Command command : commands.values()) {
            System.out.print(command.getName()+ " - ");
            System.out.println(command.getDescription());
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "справка по всем командам";
    }
}
