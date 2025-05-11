import java.util.Arrays;

public class converter {

    public static Flat fromCsv(String line) throws IllegalArgumentException {
        String[] parts = line.split(",", -1);
        for (String word:parts) {
            System.out.println(word);
        }
        if (parts.length < 14) throw new IllegalArgumentException("Неверный формат CSV-строки");

        try {
            long id = Long.parseLong(parts[0].trim());
            String name = parts[1].trim();
            Long x = Long.parseLong(parts[2].trim());
            Long y = Long.parseLong(parts[3].trim());
            float area = Float.parseFloat(parts[4].trim());
            Long numberOfRooms = Long.parseLong(parts[5].trim());
            Integer price = parts[6].trim().isEmpty() ? null : Integer.parseInt(parts[6].trim());
            View view = View.valueOf(parts[7].trim());
            Transport transport = Transport.valueOf(parts[8].trim());

            String houseName = parts[9].trim();
            Long year = Long.parseLong(parts[10].trim());
            long floors = Long.parseLong(parts[11].trim());
            Long flatsOnFloor = Long.parseLong(parts[12].trim());
            Long lifts = Long.parseLong(parts[13].trim());

            Coordinates coordinates = new Coordinates(x,y);
            House house = new House(houseName, year, floors, flatsOnFloor, lifts);

            Flat flat = new Flat(name, coordinates, area, numberOfRooms, price, view, transport, house);
//            flat.setId(id); // требуется setId вручную, если автогенерация
            return flat;

        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при разборе строки: " + e.getMessage());
        }
    }

//    public static String toCsv(Flat flat) {
//        return flat.getId() + "," +
//                flat.getName() + "," +
//                flat.getCoordinates().getX() + "," +
//                flat.getCoordinates().getY() + "," +
//                flat.getArea() + "," +
//                flat.getNumberOfRooms() + "," +
//                (flat.getPrice() != null ? flat.getPrice() : "") + "," +
//                flat.getView() + "," +
//                flat.getTransport() + "," +
//                (flat.getHouse().getName() != null ? flat.getHouse().getName() : "") + "," +
//                flat.getHouse().getYear() + "," +
//                flat.getHouse().getNumberOfFloors() + "," +
//                flat.getHouse().getNumberOfFlatsOnFloor() + "," +
//                flat.getHouse().getNumberOfLifts();
//    }
}

