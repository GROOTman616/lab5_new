package Commands;

import Common.CommandResponse;
import Common.User;
import Managers.CollectionManager;

import java.util.Scanner;

public class RemoveByNumberOfRoomsCommand implements Command{
    private final CollectionManager collectionManager;

    public RemoveByNumberOfRoomsCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    @Override
    public CommandResponse execute(Object[] args, Object data, User user) {
        if (args.length<1){
            String message = "Укажите число комнат";
            return new CommandResponse(false, message);
        }
        try {
            Long rooms = Long.parseLong((String) args[0]);
            String result = collectionManager.removeByNumberOfRooms(rooms, user);
            return new CommandResponse(true, result);
        } catch (NumberFormatException e){
            return new CommandResponse(false, "Неверный формат числа комнат");
        }
    }

    @Override
    public String getName() {
        return "remove_all_by_number_of_rooms {number}";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, значение поля numberOfRooms которого эквивалентно заданному";
    }
}
