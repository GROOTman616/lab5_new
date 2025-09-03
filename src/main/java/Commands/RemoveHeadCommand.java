package Commands;

import Common.CommandResponse;
import Managers.CollectionManager;

import java.util.Scanner;

public class RemoveHeadCommand implements Command{
    private final CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }

    @Override
    public CommandResponse execute(Object[] args, Object data, Scanner scanner) {
        String message = collectionManager.removeHead();
        return new CommandResponse(true, message);
    }

    @Override
    public String getName() {
        return "remove_head";
    }

    @Override
    public String getDescription() {
        return "вывести первый элемент коллекции и удалить его";
    }
}
