package Commands;

import Managers.CollectionManager;

import java.util.Scanner;

public class UpdateIdCommand implements Command{
    private final CollectionManager collectionManager;

    public UpdateIdCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(String[] args, Scanner scanner) {
        Long id = Long.parseLong(args[1]);
        collectionManager.updateID(id);
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}
