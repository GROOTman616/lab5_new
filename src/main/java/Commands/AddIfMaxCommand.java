package Commands;

import Common.CommandResponse;
import Data.Flat;
import Managers.CollectionManager;
import Managers.InputHelper0;

import java.util.Scanner;

public class AddIfMaxCommand implements Command{
    private final CollectionManager collectionManager;
    private final InputHelper0 inputHelper0;

    public AddIfMaxCommand(CollectionManager collectionManager, InputHelper0 inputHelper0) {
        this.collectionManager=collectionManager;
        this.inputHelper0 = inputHelper0;
    }
    @Override
    public CommandResponse execute(Object[] args, Object data, Scanner scanner) {
        Flat flat = (Flat) data;
        String result = collectionManager.addIfMax(flat);
        return new CommandResponse(true, result);
    }

    @Override
    public String getName() {
        return "add_if_max {element}";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }
}
