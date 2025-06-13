package Commands;

import Data.Flat;
import Managers.CollectionManager;
import Managers.InputHelper;

import java.util.Scanner;

public class AddComand implements Command{
    private final CollectionManager collectionManager;

    public AddComand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    @Override
    public void execute(String[] args, Scanner scanner) {
        Flat flat = InputHelper.readFlat();
        collectionManager.addFlat(flat);
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}
