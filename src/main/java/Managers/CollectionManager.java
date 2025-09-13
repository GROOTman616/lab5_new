package Managers;

import Common.User;
import Data.Flat;
import Data.Transport;
import DataBase.DBManager;

import java.io.IOException;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;

public class CollectionManager implements Serializable {
    private final ZonedDateTime initTime;
    private final DBManager dbManager;
    public PriorityQueue<Flat> flats;

    public CollectionManager(DBManager dbManager) throws IOException {
        this.dbManager = dbManager;
        this.initTime = ZonedDateTime.now();
        this.flats = dbManager.loadFlats();
    }

    public String addFlat(Flat flat, User user) {
        long id = dbManager.insertFlat(flat, user);
        if (id > 0) {
            flat.setId(id);
            flats.add(flat);
            return "Квартира успешно добавлена (id=" + id + ")";
        }
        return "Ошибка при добавлении квартиры";
    }

    public String show() {
        if (flats.isEmpty()) {
            return "Коллекция пуста";
        }
        return flats.stream()
                .sorted(Comparator.comparing(Flat::getId))
                .map(Flat::toString)
                .reduce("", (a, b) -> a + b + "\n");
    }

    public String info() {
        String info = "Тип: " + flats.getClass() + "\n" + "Дата инициализации: " + initTime + "\n" + "Количество элементов: " + flats.size();
        return info;
    }

    public String removeHead(User user) {
        Flat head = flats.peek();
        if (head == null) return "Коллекция пуста";
        if (dbManager.removeFlatById(head.getId(), user)) {
            flats.poll();
            return "Удалён: " + head;
        }
        return "Ошибка: нельзя удалить чужую квартиру";
    }

    public String clear(User user) {
        if (dbManager.clearFlats(user)) {
            flats.removeIf(f -> dbManager.isFlatOwnedByUser(f.getId(), user));
            return "Коллекция очищена";
        }
        return "Ошибка при очистке коллекции";
    }

    public String updateID(long id, Flat newFlat, User user) {
        if (dbManager.updateFlat(id, newFlat, user)) {
            flats.removeIf(f -> f.getId() == id);
            newFlat.setId(id);
            flats.add(newFlat);
            return "Элемент обновлён";
        }
        return "Элемент с таким id не найден или не принадлежит вам";
    }

    public String removeID(long id, User user) {
        if (dbManager.removeFlatById(id, user)) {
            flats.removeIf(f->f.getId()==id);
            return "Элемент удалён";
        }
        return "Элемент не найден или не принадлежит вам";
    }
    public String removeByNumberOfRooms(Long numberOfRooms, User user) {
        StringBuilder result = new StringBuilder();
        for (Flat f : new ArrayList<>(flats)) {
            if (f.getNumberOfRooms().equals(numberOfRooms)) {
                if (dbManager.removeFlatById(f.getId(), user)) {
                    flats.remove(f);
                    result.append("Удалён: ").append(f).append("\n");
                }
            }
        }
        return result.isEmpty() ? "Квартир с таким количеством комнат нет" : result.toString();
    }

    public PriorityQueue<Flat> priceFilter(Integer price){
        PriorityQueue<Flat> flatsAnswer = new PriorityQueue<>();
        for (Flat f: flats) {
            if (f.getPrice()>price){
                flatsAnswer.add(f);
            }
        }
        return flatsAnswer;
    }

    public String transportOut() {
        ArrayList<Transport> trlist = new ArrayList<>();
        for (Flat f: flats) {
            Transport tr = f.getTransport();
            trlist.add(tr);
        }
        Collections.sort(trlist);
        String result = String.valueOf(trlist);
        return result;
    }

    public String addIfMax(Flat flat, User user) {
        Flat maxflat = Collections.max(flats);
        String result;
        if (flats.isEmpty()) {
            return addFlat(flat, user);
        }
        if (flat.compareTo(maxflat)>0) {
            return addFlat(flat, user);
        }
        else {
            flat.fixId();
            result = "Объект не подходит";
            return result;
        }
    }

    public String addIfMin(Flat flat, User user) {
        Flat minflat = Collections.min(flats);
        String result;
        if (flat.compareTo(minflat)<0) {
            return addFlat(flat, user);
        }
        else {
            flat.fixId();
            result = "Объект не подходит";
            return result;
        }
    }

    public PriorityQueue<Flat> getCollection() {
        return flats;
    }
}

