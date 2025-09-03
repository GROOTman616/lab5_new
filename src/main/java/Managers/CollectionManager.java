package Managers;

import Data.Flat;
import Data.Transport;

import java.io.IOException;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;

public class CollectionManager implements Serializable {
    private final ZonedDateTime initTime;
    public String filename;
    FileManager fmanager = new FileManager();
    public PriorityQueue<Flat> flats;

    public CollectionManager(String filename) throws IOException {
        this.filename = filename;
        this.initTime = ZonedDateTime.now();
        this.flats = fmanager.readFromCsv(filename);
    }

    public void addFlat(Flat flat) {
        flats.add(flat);
    }

    public String show() {
        if (flats.isEmpty()){
            return "Коллекция пуста";
        }
        return flats.stream()
                .sorted(Comparator.comparing(Flat::getId))
                .map(Flat::toString)
                .reduce("", (a, b) -> a+b + "\n");
    }

    public String info() {
        String info = "Тип: " + flats.getClass() + "\n" + "Дата инициализации: " + initTime + "\n" + "Количество элементов: " + flats.size();
        return info;
    }

    public String removeHead() {
        Flat head = flats.poll();
        String result = "Удалён: " + head;
        return result;
    }

    public String clear() {
        flats.clear();
        String result = "Коллекция очищена";
        return result;
    }

    public String updateID(long id, Flat newFlat) {
        Flat oldFlat = null;
        String message;
        for(Flat f: flats) {
            if (f.getId()==id) {
                oldFlat = f;
                break;
            }
        }
        if (oldFlat==null) {
            message = "Элемент c таким id не найден";
            return message;
        }
        else {
            flats.remove(oldFlat);
            newFlat.setId(id);
            flats.add(newFlat);
            message = "Элемент обновлён";
            return message;
        }
    }

    public String removeID(long id) {
        Flat oldFlat = null;
        for(Flat f: flats) {
            if (f.getId()==id) {
                oldFlat = f;
                break;
            }
        }
        if (oldFlat==null) {
            return "Элемент не найден";
        }
        else {
            flats.remove(oldFlat);
            return "Элемент удалён";
        }
    }
    public String removeByNumberOfRooms(Long numberOfRooms) {
        Iterator<Flat> iterator = flats.iterator();
        String result = "";
        while (iterator.hasNext()) {
            Flat f = iterator.next();
            if (f.getNumberOfRooms()==numberOfRooms) {
                iterator.remove();
                result += "Удалён: "+ f + "\n";
            }
        }
        if (result.equals("")) {
            return "Квартир с таким количеством комнат нет";
        }
        return result;
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

    public String addIfMax(Flat flat) {
        Flat maxflat = Collections.max(flats);
        String result;
        if (flat.compareTo(maxflat)>0) {
            flats.add(flat);
            result = "Объект успешно добавлен";
            return result;
        }
        else {
            flat.fixId();
            result = "Объект не подходит";
            return result;
        }
    }

    public String addIfMin(Flat flat) {
        Flat minflat = Collections.min(flats);
        String result;
        if (flat.compareTo(minflat)<0) {
            flats.add(flat);
            result = "Объект успешно добавлен";
            return result;
        }
        else {
            flat.fixId();
            result = "Объект не подходит";
            return result;
        }
    }

    public PriorityQueue<Flat> getCollection(){
        return flats;
    }
    public String getFilename() {
        return filename;
    }
}

