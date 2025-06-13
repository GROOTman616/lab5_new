package Commands;

import Managers.CollectionManager;

import java.util.Scanner;

public class ClearCommand implements Command{
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager=collectionManager;
    }
    @Override
    public void execute(String[] args, Scanner scanner) {
        collectionManager.clear();
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
