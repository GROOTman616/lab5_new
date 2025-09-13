package Commands;

import Common.CommandResponse;
import Common.User;
import Managers.CommandManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExecuteScriptCommand implements Command{
    private final CommandManager commandManager;

    public ExecuteScriptCommand(CommandManager commandManager){
        this.commandManager=commandManager;
    }
    ArrayList<String> scripts= new ArrayList<>();
    @Override
    public CommandResponse execute(Object[] args, Object data, User user) throws IOException {
        if (args.length < 1) {
            return new CommandResponse(false, "Укажите имя скрипта");
        }
        String scriptName = (String) args[0];
        try (Scanner scriptScanner=new Scanner(new File(scriptName))){
            scripts.add(scriptName);
            while (scriptScanner.hasNextLine()) {
                String line = scriptScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                System.out.println("Выполняю: " + line);
                String[] parts = line.split(" ", 2);
                String commandName = parts[0].toLowerCase().trim();
                String[] commandArgs = parts.length > 1 ? parts[1].split(" ") : new String[0];
                if (commandName.equals("execute_script")){
                    if (scripts.contains(commandArgs[0])){
                        return new CommandResponse(false, "Ошибка: Рекурсивный вывод скриптов запрщён");
                    }
                    scripts.add(commandArgs[0]);
                }
                commandManager.executeCommand(line, data, user);
            }
            return new CommandResponse(true, "Скрипт выполнен");
        } catch (FileNotFoundException e) {
            return new CommandResponse(false, "Файл скрипта не найден: " + scriptName);
        } catch (Exception e) {
            return new CommandResponse(false, "Ошибка выполнения скрипта: " + e.getMessage());
        }
    }
    @Override
    public String getName() {
        return "execute_script {script_name}";
    }

    @Override
    public String getDescription() {
        return "выполнить команды по скрипту из указанного файла";
    }
}
