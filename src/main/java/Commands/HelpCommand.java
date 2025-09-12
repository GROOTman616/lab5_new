package Commands;

import Common.CommandResponse;
import Common.User;
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
    public CommandResponse execute(Object[] args, Object data, User user) throws IOException {
        HashMap<String, Command> commands = commandManager.getCommands();
        String response = "";
        for (Command command : commands.values()) {
            response+=(command.getName()+ " - " + command.getDescription()+"\n");
        }
        return new CommandResponse(true, response);
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
