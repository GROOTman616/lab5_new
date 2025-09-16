package Managers;

import Data.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Класс для обработки пользовательского ввода с валидацией
 */
public class InputHelper {
    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    // Основные методы чтения данных

    public String readString(String prompt, boolean allowEmpty) {
        while (true) {
            System.out.print(prompt + (allowEmpty ? " (можно пустое): " : ": "));
            String input = scanner.nextLine().trim();
            if (!allowEmpty && input.isEmpty()) {
                System.out.println("Ошибка: значение не может быть пустым");
                continue;
            }
            return input.isEmpty() ? null : input;
        }
    }

    public int readInt(String prompt, int minValue) {
        while (true) {
            try {
                System.out.print(prompt + " (минимум " + minValue + "): ");
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value < minValue) {
                    System.out.println("Ошибка: значение должно быть не меньше " + minValue);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число");
            }
        }
    }

    public long readLong(String prompt, long maxValue) {
        while (true) {
            try {
                System.out.print(prompt + " (максимум " + maxValue + "): ");
                long value = Long.parseLong(scanner.nextLine().trim());
                if (value > maxValue) {
                    System.out.println("Ошибка: значение должно быть не больше " + maxValue);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число");
            }
        }
    }

    public float readFloat(String prompt, float maxValue) {
        while (true) {
            try {
                System.out.print(prompt + " (максимум " + maxValue + "): ");
                float value = Float.parseFloat(scanner.nextLine().trim());
                if (value > maxValue) {
                    System.out.println("Ошибка: значение должно быть не больше " + maxValue);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите другое число");
            }
        }
    }

    public Integer readInt(String prompt, int minValue, boolean canBeNull) {
        if (!canBeNull) {
            readInt(prompt, minValue);
        }
        while (true) {
            System.out.print(prompt + " (минимум " + minValue + " или пустое): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return null;
            }
            try {
                int value = Integer.parseInt(input);
                if (value < minValue) {
                    System.out.println("Ошибка: значение должно быть не меньше " + minValue);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число или оставьте пустым");
            }
        }
    }

    public <T extends Enum<T>> T readEnum(Class<T> enumClass, String prompt) {
        System.out.println(prompt + ". Допустимые значения: " + Arrays.toString(enumClass.getEnumConstants()));
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return null;
            }
            try {
                return Enum.valueOf(enumClass, input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: введите одно из допустимых значений или оставьте пустым");
            }
        }
    }

    // Методы для чтения сложных объектов

    public Coordinates readCoordinates() {
        System.out.println("--- Ввод координат ---");
        long x = readLong("X", 817);
        long y = readLong("Y", Long.MAX_VALUE);
        return new Coordinates(x, y);
    }

    public House readHouse() {
        System.out.println("--- Ввод данных дома ---");
        String name = readString("Имя", true);
        Long year = readLong("Год", Long.MAX_VALUE);
        if (year != null && year <= 0) {
            System.out.println("Ошибка: год должен быть больше 0");
            return readHouse();
        }
        long numberOfFloors = readLong("Количество этажей: ", Long.MAX_VALUE);
        if (numberOfFloors <= 0) {
            System.out.println("Ошибка: количество этажей должно быть больше 0");
            return readHouse();
        }
        Long numberOfFlatsOnFloor = readLong("Количество квартир на этаже", Long.MAX_VALUE);
        if (numberOfFlatsOnFloor != null && year <= 0) {
            System.out.println("Ошибка: количество квартир на этаже должно быть больше 0");
            return readHouse();
        }
        Long numberOfLifts = readLong("Количество лифтов", Long.MAX_VALUE);
        if (numberOfLifts != null && year <= 0) {
            System.out.println("Ошибка: количество лифтов должно быть больше 0");
            return readHouse();
        }
        return new House(name, year, numberOfFloors, numberOfFlatsOnFloor, numberOfLifts);
    }

    public Flat readFlat() {
        System.out.println("--- Ввод данных квартиры ---");
        String name = readString("Название квартиры", false);
        Coordinates coordinates = readCoordinates();
        float area = readFloat("Площадь", Float.MAX_VALUE);
        if (area <= 0) {
            System.out.println("Ошибка: площадь должна быть больше 0");
            return readFlat();
        }
        Long numberOfRooms = readLong("Число комнат", 9);
        if (numberOfRooms != null && numberOfRooms <= 0) {
            System.out.println("Ошибка: количество комнат должно быть больше 0");
            return readFlat();
        }
        Integer price = readInt("Цена", 1, true);
        View view = readEnum(View.class, "Вид");
        Transport transport = readEnum(Transport.class, "Транспорт");
        House house = readHouse();
        return new Flat(name, coordinates, area, numberOfRooms, price, view, transport, house);
    }
}
