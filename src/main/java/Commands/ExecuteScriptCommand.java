package Commands;

import Managers.CommandManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ExecuteScriptCommand implements Command{
    private final CommandManager commandManager;

    public ExecuteScriptCommand(CommandManager commandManager){
        this.commandManager=commandManager;
    }
    @Override
    public void execute(String[] args, Scanner scanner) throws IOException {
        if (args.length < 2) {
            System.out.println("Укажите имя скрипта");
            return;
        }
        String scriptName = args[1];
        try (Scanner scriptScanner = new Scanner(new File(scriptName))) {
            while (scriptScanner.hasNextLine()) {
                String line = scriptScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                System.out.println("Выполняю: " + line);
                String[] parts = line.split(" ", 2);
                String commandName = parts[0];
                String[] commandArgs = parts[1].split(" ");
                if ((commandName.equals("execute_script") && commandArgs[0].equals(scriptName))) {
                    System.out.println("Ошибка: Рекурсивный вызов скриптов запрещен");
                    continue;
                }
                if (commandName != null) {
                    commandManager.executeCommand(line, scanner);
                } else {
                    System.out.println("Неизвестная команда: " + commandName);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл скрипта не найден: " + scriptName);
        } catch (Exception e) {
            System.out.println("Ошибка выполнения скрипта: " + e.getMessage());
        }
    }
    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "выполнить команды по скрипту из указанного файла";
    }
}
