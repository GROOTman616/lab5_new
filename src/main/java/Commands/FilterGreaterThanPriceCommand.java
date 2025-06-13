package Commands;

import Managers.CollectionManager;

import java.util.Scanner;

public class FilterGreaterThanPriceCommand implements Command{
    private final CollectionManager collectionManager;

    public FilterGreaterThanPriceCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    @Override
    public void execute(String[] args, Scanner scanner) {
        if (args.length < 2) {
            System.out.println("Укажите цену");
            return;
        }
        try{
            Integer price = Integer.parseInt(args[1]);
            collectionManager.priceFilter(price);
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат цены");
        }
    }

    @Override
    public String getName() {
        return "filter_greater_than_price";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля price которых больше заданного";
    }
}
