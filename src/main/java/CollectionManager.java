import java.time.ZonedDateTime;
import java.util.PriorityQueue;

public class CollectionManager {
    private PriorityQueue<Flat> flats = new PriorityQueue<>();
    private ZonedDateTime initTime;

    public CollectionManager() {
        this.initTime = ZonedDateTime.now();
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
    }

    public PriorityQueue<Flat> getCollection() {
        return flats;
    }

    // другие методы для add_if_max, update, etc.
}

