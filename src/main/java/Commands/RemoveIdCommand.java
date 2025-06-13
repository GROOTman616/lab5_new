package Commands;

import Managers.CollectionManager;

import java.util.Scanner;

public class RemoveIdCommand implements Command{
    private final CollectionManager collectionManager;

    public RemoveIdCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    @Override
    public void execute(String[] args, Scanner scanner) {
        if (args.length<2) {
            System.out.println("Укажите id элемента");
            return;
        }
        try {
            Long id = Long.parseLong(args[1]);
            collectionManager.removeID(id);
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат id");
        }
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }
}
