package Commands;

import Common.CommandResponse;
import Common.User;
import Data.Flat;
import Managers.CollectionManager;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class FilterGreaterThanPriceCommand implements Command{
    private final CollectionManager collectionManager;

    public FilterGreaterThanPriceCommand(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    @Override
    public CommandResponse execute(Object[] args, Object data, User user) {
        if (args.length < 1) {
            String message = "Укажите цену";
            return new CommandResponse (false, message);
        }
        try{
            Integer price = Integer.parseInt((String) args[0]);
            PriorityQueue<Flat> flatsAnswer= collectionManager.priceFilter(price);
            String message;
            if (flatsAnswer.isEmpty()) {
                message = "Квартир с такой ценой нет";
            } else {
                message = flatsAnswer.stream()
                        .sorted(Comparator.comparing(Flat::getId))
                        .map(Flat::toString)
                        .reduce("", (a, b) -> a+b + "\n");
            }
            return new CommandResponse(true, message);
        } catch (NumberFormatException e) {
            return new CommandResponse(false, "Неверный формат цены");
        }
    }

    @Override
    public String getName() {
        return "filter_greater_than_price {price}";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля price которых больше заданного";
    }
}
