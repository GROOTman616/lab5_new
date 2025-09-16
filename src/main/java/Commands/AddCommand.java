package Commands;

import Common.CommandResponse;
import Common.User;
import Data.Flat;
import Managers.CollectionManager;
import Managers.InputHelper0;

import java.util.Scanner;

public class AddCommand implements Command{
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager, InputHelper0 inputHelper0){
        this.collectionManager=collectionManager;
    }
    @Override
    public CommandResponse execute(Object[] args, Object data, User user) {
        Flat flat = (Flat) data;
        String result = collectionManager.addFlat(flat, user);
        return new CommandResponse(true, result);
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
