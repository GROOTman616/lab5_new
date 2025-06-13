package Commands;

import Data.Flat;
import Managers.CollectionManager;
import Managers.InputHelper;

import java.util.Scanner;

public class AddIfMinCommand implements Command{
    private final CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        this.collectionManager=collectionManager;
    }
    @Override
    public void execute(String[] args, Scanner scanner) {
        Flat flat = InputHelper.readFlat();
        collectionManager.addIfMin(flat);
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }
}
