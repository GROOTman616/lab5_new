package Commands;

import Common.CommandResponse;
import Common.User;

import java.util.Scanner;

public class ExitCommand implements Command{
    @Override
    public CommandResponse execute(Object[] args, Object data, User user) {
        return new CommandResponse(true, "Завершение программы...");
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "завершить программу (без сохранения в файл)";
    }
}
