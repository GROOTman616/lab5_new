package Commands;

import Managers.CollectionManager;

import java.util.Scanner;

public class RemoveByNumberOfRoomsCommand implements Command{
    private final CollectionManager collectionManager;

    public RemoveByNumberOfRoomsCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    @Override
    public void execute(String[] args, Scanner scanner) {
        if (args.length<2){
            System.out.println("Укажите число комнат");
            return;
        }
        try {
            Long rooms = Long.parseLong(args[1]);
            collectionManager.removeByNumberOfRooms(rooms);
        } catch (NumberFormatException e){
            System.err.println("Неверный формат числа комнат");
        }
    }

    @Override
    public String getName() {
        return "remove_all_by_number_of_rooms";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, значение поля numberOfRooms которого эквивалентно заданному";
    }
}
