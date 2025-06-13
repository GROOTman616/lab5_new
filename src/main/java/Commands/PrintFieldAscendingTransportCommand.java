package Commands;

import Managers.CollectionManager;

import java.util.Scanner;

public class PrintFieldAscendingTransportCommand implements Command{
    private final CollectionManager collectionManager;

    public PrintFieldAscendingTransportCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }

    @Override
    public void execute(String[] args, Scanner scanner) {
        collectionManager.transportOut();
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
