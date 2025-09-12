package Commands;

import Common.CommandResponse;
import Common.User;
import Data.Flat;
import Managers.CollectionManager;
import Managers.InputHelper0;

import java.util.Scanner;

public class UpdateIdCommand implements Command{
    private final CollectionManager collectionManager;

    public UpdateIdCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public CommandResponse execute(Object[] args, Object data, User user) {
        if (args.length<1) {
            return new CommandResponse(false, "Укажите id элемента");
        }
        try {
            Long id = Long.parseLong((String) args[0]);
            Flat flat = (Flat) data;
            String result = collectionManager.updateID(id, flat, user);
            return new CommandResponse(true, result);
        } catch (NumberFormatException e){
            return new CommandResponse(false, "Неверный формат id");
        }
    }

    @Override
    public String getName() {
        return "update {id}";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}
