package Commands;

import Common.CommandResponse;
import Data.Flat;
import Managers.CollectionManager;
import Managers.InputHelper0;

import java.util.Scanner;

public class AddIfMinCommand implements Command{
    private final CollectionManager collectionManager;
    private final InputHelper0 inputHelper0;

    public AddIfMinCommand(CollectionManager collectionManager, InputHelper0 inputHelper0) {
        this.collectionManager=collectionManager;
        this.inputHelper0=inputHelper0;
    }
    @Override
    public CommandResponse execute(Object[] args, Object data, Scanner scanner) {
        Flat flat = (Flat) data;
        String result = collectionManager.addIfMin(flat);
        return new CommandResponse(true, result);
    }

    @Override
    public String getName() {
        return "add_if_min {element}";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }
}
