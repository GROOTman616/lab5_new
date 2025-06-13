package Commands;

import java.util.Scanner;

public class ExitCommand implements Command{
    @Override
    public void execute(String[] args, Scanner scanner) {
        System.out.println("Завершение программы...");
        System.exit(0);
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
