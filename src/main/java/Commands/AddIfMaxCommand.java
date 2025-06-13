package Commands;

import Data.Flat;
import Managers.CollectionManager;
import Managers.InputHelper;

import java.util.Scanner;

public class AddIfMaxCommand implements Command{
    private final CollectionManager collectionManager;

    public AddIfMaxCommand(CollectionManager collectionManager) {
        this.collectionManager=collectionManager;
    }
    @Override
    public void execute(String[] args, Scanner scanner) {
        Flat flat = InputHelper.readFlat();
        collectionManager.addIfMax(flat);
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }
}
