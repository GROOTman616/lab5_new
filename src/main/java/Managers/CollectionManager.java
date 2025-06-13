package Managers;

import Data.Flat;
import Data.Transport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

public class CollectionManager {
    private final ZonedDateTime initTime;
    public String filename;
    FileManager fmanager = new FileManager();
    public PriorityQueue<Flat> flats;

    public CollectionManager(String filename) throws FileNotFoundException {
        this.filename = filename;
        this.initTime = ZonedDateTime.now();
        this.flats = fmanager.readFromCsv(filename);
    }

    public void addFlat(Flat flat) {
        flats.add(flat);
    }

    public void show() {
        flats.forEach(System.out::println);
    }

    public void info() {
        System.out.println("Тип: " + flats.getClass());
        System.out.println("Дата инициализации: " + initTime);
        System.out.println("Количество элементов: " + flats.size());
    }

    public void removeHead() {
        Flat head = flats.poll();
        System.out.println("Удалён: " + head);
    }

    public void clear() {
        flats.clear();
        System.out.println("Коллекция очищена");
    }

    public void updateID(long id) {
        Flat oldFlat = null;
        for(Flat f: flats) {
            if (f.getId()==id) {
                oldFlat = f;
                break;
            }
        }
        if (oldFlat==null) {
            System.out.println("Элемент не найден");
        }
        else {
            flats.remove(oldFlat);
            Flat newFlat = InputHelper.readFlat();
            newFlat.setId(id);
            flats.add(newFlat);
        }
    }

    public void removeID(long id) {
        Flat oldFlat = null;
        for(Flat f: flats) {
            if (f.getId()==id) {
                oldFlat = f;
                break;
            }
        }
        if (oldFlat==null) {
            System.out.println("Элемент не найден");
        }
        else {
            flats.remove(oldFlat);
            System.out.println("Элемент удалён");
        }
    }
    public void removeByNumberOfRooms(Long numberOfRooms) {
        Iterator<Flat> iterator = flats.iterator();
        while (iterator.hasNext()) {
            Flat f = iterator.next();
            if (f.getNumberOfRooms()==numberOfRooms) {
                iterator.remove();
            }
        }
    }

    public void priceFilter(Integer price){
        for (Flat f: flats) {
            if (f.getPrice()>price){
                System.out.println(f);
            }
        }
    }

    public void transportOut() {
        ArrayList<Transport> trlist = new ArrayList<>();
        for (Flat f: flats) {
            Transport tr = f.getTransport();
            trlist.add(tr);
        }
        Collections.sort(trlist);
        System.out.println(trlist);
    }

    public void addIfMax(Flat flat) {
        Flat maxflat = Collections.max(flats);
        if (flat.compareTo(maxflat)>0) {
            flats.add(flat);
            System.out.println("Объект успешно добавлен");
        }
        else {
            flat.fixId();
            System.out.println("Объект не подходит");
        }
    }

    public void addIfMin(Flat flat) {
        Flat minflat = Collections.min(flats);
        if (flat.compareTo(minflat)<0) {
            flats.add(flat);
            System.out.println("Объект успешно добавлен");
        }
        else {
            flat.fixId();
            System.out.println("Объект не подходит");
        }
    }

    public PriorityQueue<Flat> getCollection(){
        return flats;
    }
    public String getFilename() {
        return filename;
    }
}

