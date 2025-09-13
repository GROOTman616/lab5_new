package Commands;

import Common.CommandResponse;
import Common.User;
import Managers.CollectionManager;

import java.util.Scanner;

public class PrintFieldAscendingTransportCommand implements Command{
    private final CollectionManager collectionManager;

    public PrintFieldAscendingTransportCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }

    @Override
    public CommandResponse execute(Object[] args, Object data, User user) {
        String result = collectionManager.transportOut();
        return new CommandResponse(true, result);
    }

    @Override
    public String getName() {
        return "print_field_ascending_transport";
    }

    @Override
    public String getDescription() {
        return "вывести значения поля transport всех элементов в порядке возрастания";
    }
}
