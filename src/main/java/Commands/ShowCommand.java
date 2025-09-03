package Commands;

import Common.CommandResponse;
import Managers.CollectionManager;

import java.util.Scanner;

public class ShowCommand implements Command{
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    @Override
    public CommandResponse execute(Object[] args, Object data, Scanner scanner) {
        String collection = collectionManager.show();
        return new CommandResponse(true, collection);
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
