import java.sql.SQLOutput;
import java.time.ZonedDateTime;
import java.util.*;

public class CollectionManager {
    private ZonedDateTime initTime;

    public CollectionManager() {
        this.initTime = ZonedDateTime.now();
    }

    public void addFlat(PriorityQueue<Flat> flats) {
        Flat flat = InputHelper.readFlat();
        flats.add(flat);
    }

    public void show(PriorityQueue<Flat> flats) {
        flats.forEach(System.out::println);
    }

    public void info(PriorityQueue<Flat> flats) {
        System.out.println("Тип: " + flats.getClass());
        System.out.println("Дата инициализации: " + initTime);
        System.out.println("Количество элементов: " + flats.size());
    }

    public void removeHead(PriorityQueue<Flat> flats) {
        Flat head = flats.poll();
        System.out.println("Удалён: " + head);
    }

    public void clear(PriorityQueue<Flat> flats) {
        flats.clear();
        System.out.println("Коллекция очищена");
    }

    public void updateID(PriorityQueue<Flat> flats, long id) {
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
        flats.remove(oldFlat);
        Flat newFlat = InputHelper.readFlat();
        newFlat.setId(id);
        flats.add(newFlat);
    }

    public void removeID(PriorityQueue<Flat> flats, long id) {
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
        flats.remove(oldFlat);
    }
    public void removeByNumberOfRooms(PriorityQueue<Flat> flats, Long numberOfRooms) {
        Iterator<Flat> iterator = flats.iterator();
        while (iterator.hasNext()) {
            Flat f = iterator.next();
            if (f.getNumberOfRooms()==numberOfRooms) {
                iterator.remove();
            }
        }
    }

    public void priceFilter(PriorityQueue<Flat> flats, Integer price){
        for (Flat f: flats) {
            if (f.getPrice()>price){
                System.out.println(f);
            }
        }
    }

    public void transportOut(PriorityQueue<Flat> flats) {
        ArrayList<Transport> trlist = new ArrayList<>();
        for (Flat f: flats) {
            Transport tr = f.getTransport();
            trlist.add(tr);
        }
        Collections.sort(trlist);
        System.out.println(trlist);
    }

    public void addIfMax(PriorityQueue<Flat> flats) {
        Flat flat = InputHelper.readFlat();
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

    public void addIfMin(PriorityQueue<Flat> flats) {
        Flat flat = InputHelper.readFlat();
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
}

