package Commands;

import Managers.CollectionManager;

import java.util.Scanner;

public class RemoveHeadCommand implements Command{
    private final CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }

    @Override
    public void execute(String[] args, Scanner scanner) {
        collectionManager.removeHead();
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
