package Commands;

import Common.CommandResponse;
import Managers.CollectionManager;

import java.util.Scanner;

public class RemoveIdCommand implements Command{
    private final CollectionManager collectionManager;

    public RemoveIdCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    @Override
    public CommandResponse execute(Object[] args, Object data, Scanner scanner) {
        if (args.length<1) {
            return new CommandResponse(false, "Укажите id элемента");
        }
        try {
            Long id = Long.parseLong((String) args[0]);
            String result = collectionManager.removeID(id);
            return new CommandResponse(true, result);
        } catch (NumberFormatException e) {
            return new CommandResponse(true, "Неверный формат id");
        }
    }

    @Override
    public String getName() {
        return "remove_by_id {id}";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }
}
