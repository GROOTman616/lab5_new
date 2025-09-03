package Commands;

import Common.CommandResponse;
import Data.Flat;
import Managers.CollectionManager;
import Managers.InputHelper0;

import java.util.Scanner;

public class AddCommand implements Command{
    private final CollectionManager collectionManager;
    private final InputHelper0 inputHelper0;

    public AddCommand(CollectionManager collectionManager, InputHelper0 inputHelper0){
        this.collectionManager=collectionManager;
        this.inputHelper0=inputHelper0;
    }
    @Override
    public CommandResponse execute(Object[] args, Object data, Scanner scanner) {
        Flat flat = (Flat) data;
        collectionManager.addFlat(flat);
        return new CommandResponse(true, "Элемент добавлен");
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
