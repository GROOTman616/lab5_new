package Data;

import java.time.ZonedDateTime;

public class Flat implements Comparable<Flat>{
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float area; //Значение поля должно быть больше 0
    private Long numberOfRooms; //Максимальное значение поля: 9, Значение поля должно быть больше 0
    private Integer price; //Поле может быть null, Значение поля должно быть больше 0
    private View view; //Поле не может быть null
    private Transport transport; //Поле не может быть null
    private House house; //Поле не может быть null

    private static long idCounter = 1;

    public Flat(String name, Coordinates coordinates, float area, Long numberOfRooms,
                Integer price, View view, Transport transport, House house) {
        this.id = idCounter++;
        this.creationDate = ZonedDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.price = price;
        this.view = view;
        this.transport = transport;
        this.house = house;
    }
    public Flat(){

    }
    @Override
    public int compareTo(Flat other) {
        return Float.compare(this.area, other.area); // сортировка по площади
    }
    @Override
    public String toString() {
        return "Data.Flat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", area=" + area +
                ", rooms=" + numberOfRooms +
                ", price=" + price +
                ", view=" + view +
                ", transport=" + transport +
                ", house=" + house +
                '}';
    }

    public long getId() {
        return id;
    }

    public Long getNumberOfRooms() {
        return numberOfRooms;
    }

    public Integer getPrice() {
        return price;
    }

    public void setId(long newId) {
        this.id = newId;
    }

    public Transport getTransport() {
        return transport;
    }

    public void fixId() {
        idCounter = idCounter-1;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public float getArea() {
        return area;
    }

    public View getView() {
        return view;
    }

    public House getHouse() {
        return house;
    }
}
