package Commands;

import Managers.CollectionManager;

import java.util.Scanner;

public class ShowCommand implements Command{
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    @Override
    public void execute(String[] args, Scanner scanner) {
        collectionManager.show();
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
