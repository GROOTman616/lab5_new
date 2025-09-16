package Commands;

import Common.CommandResponse;
import Common.User;
import Managers.CollectionManager;
import Managers.FileManager;

import java.io.IOException;
import java.util.Scanner;

public class SaveCommand implements Command{
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    @Override
    public CommandResponse execute(Object[] args, Object data, User user) throws IOException {
        try {
//            FileManager.writeToCsv(collectionManager.getFilename(), collectionManager.getCollection());
            System.out.println("Коллекция сохранена");
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении коллекции: "+e.getMessage());
        }
        return null;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}