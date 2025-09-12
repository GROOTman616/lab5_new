package Commands;

import Common.CommandResponse;
import Common.User;
import Managers.CollectionManager;

import java.util.Scanner;

public class RemoveHeadCommand implements Command{
    private final CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }

    @Override
    public CommandResponse execute(Object[] args, Object data, User user) {
        String message = collectionManager.removeHead(user);
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
