package Commands;
import Common.CommandResponse;
import Managers.CollectionManager;

import java.io.Serializable;
import java.util.Scanner;

public class InfoCommand implements Command, Serializable {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResponse execute(Object[] args, Object data, Scanner scanner) {
        String info = collectionManager.info();
        return new CommandResponse(true, info);
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
