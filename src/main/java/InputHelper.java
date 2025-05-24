import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class InputHelper {
    public static Flat readFlat() {
        Scanner sc=new Scanner(System.in);
        System.out.print("Введите название квартиры: ");
        String name = sc.nextLine();
        System.out.print("Введите координату x: ");
        Long x = sc.nextLong();
        System.out.print("Введите координату y: ");
        Long y = sc.nextLong();
        System.out.print("Введите площадь: ");
        float area = sc.nextFloat();
        System.out.print("Введите число комнат: ");
        Long numberOfRooms = sc.nextLong();
        System.out.print("Введите цену: ");
        Integer price = sc.nextInt();
        sc.nextLine();
        System.out.print("Введите вид из окна (Доступные значения:"+ Arrays.toString(View.values())+"): ");
        String view0 = sc.nextLine().toUpperCase();
        View view = null;
        view = View.valueOf(view0);
        System.out.print("Введите транспорт (Доступные значения:"+ Arrays.toString(Transport.values())+"): ");
        String transport0 = sc.nextLine();
        Transport transport = Transport.valueOf(transport0.trim());
        System.out.print("Введите название дома: ");
        String houseName = sc.nextLine();
        System.out.print("Введите год: ");
        Long year = sc.nextLong();
        System.out.print("Введите кол-во этажей: ");
        long floors = sc.nextLong();
        System.out.print("Введите кол-во квартир на этаже: ");
        Long flatsOnFloor = sc.nextLong();
        System.out.print("Введите кол-во лифтов: ");
        Long lifts = sc.nextLong();
        Coordinates coordinates = new Coordinates(x,y);
        House house = new House(houseName, year, floors, flatsOnFloor, lifts);
        Flat flat = new Flat(name, coordinates, area, numberOfRooms, price, view, transport, house);
        return flat;
    }
}
