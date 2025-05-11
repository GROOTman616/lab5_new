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
}
