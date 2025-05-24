public class House {
    private String name; //Поле может быть null
    private Long year; //Значение поля должно быть больше 0
    private long numberOfFloors; //Значение поля должно быть больше 0
    private Long numberOfFlatsOnFloor; //Значение поля должно быть больше 0
    private Long numberOfLifts; //Значение поля должно быть больше 0
    public House (String name, Long year, long numberOfFloors, Long numberOfFlatsOnFloor, Long numberOfLifts) {
        this.name=name;
        this.year=year;
        this.numberOfFloors=numberOfFloors;
        this.numberOfFlatsOnFloor=numberOfFlatsOnFloor;
        this.numberOfLifts=numberOfLifts;
    }
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", number of floors=" + numberOfFloors +
                ", number of flats on floor=" + numberOfFlatsOnFloor +
                ", number of lifts=" + numberOfLifts +
                '}';
    }

    public String getName() {
        return name;
    }

    public Long getYear() {
        return year;
    }

    public long getNumberOfFloors() {
        return numberOfFloors;
    }

    public Long getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    public Long getNumberOfLifts() {
        return numberOfLifts;
    }
}
