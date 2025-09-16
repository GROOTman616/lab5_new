package Commands;

import Common.CommandResponse;
import Common.User;
import Managers.CollectionManager;

import java.util.Scanner;

public class ClearCommand implements Command{
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager=collectionManager;
    }
    @Override
    public CommandResponse execute(Object[] args, Object data, User user) {
        String result = collectionManager.clear(user);
        return new CommandResponse(true, result);
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
