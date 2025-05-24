import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.ZonedDateTime;
import java.util.*;

public class CollectionManager {
    private final ZonedDateTime initTime;
    static String filename = "C:\\Users\\rutma\\OneDrive\\Рабочий стол\\Test1.csv";

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

    public void execute_script(PriorityQueue<Flat> flats, String filename) throws IOException {
        try (Scanner scriptScanner = new Scanner(new File(filename))) {
            CollectionManager colmanager = new CollectionManager();
            while (scriptScanner.hasNextLine()) {
                String line = scriptScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                System.out.println("Выполняю: " + line);
                String[] parts = line.split(" ", 2);
                String commandName = parts[0].toLowerCase();
                String[] commandArgs = parts.length > 1 ? parts[1].split(" ") : new String[0];

                if (commandName.equals("execute_script")) {
                    System.out.println("Ошибка: Рекурсивный вызов скриптов запрещен");
                    continue;
                }
                if (commandName != null) {
                    switch (commandName){
                        case "help":
                            System.out.println("СПРАВКА\n"
                                    + "help : вывести справку по доступным командам\n"
                                    + "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n"
                                    + "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n"
                                    + "add {element} : добавить новый элемент в коллекцию\n"
                                    + "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n"
                                    + "remove_by_id id : удалить элемент из коллекции по его id\n"
                                    + "clear : очистить коллекцию\n"
                                    + "save : сохранить коллекцию в файл\n"
                                    + "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n"
                                    + "exit : завершить программу (без сохранения в файл)\n"
                                    + "remove_head : вывести первый элемент коллекции и удалить его\n"
                                    + "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n"
                                    + "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n"
                                    + "remove_all_by_number_of_rooms numberOfRooms : удалить из коллекции все элементы, значение поля numberOfRooms которого эквивалентно заданному\n"
                                    + "filter_greater_than_price price : вывести элементы, значение поля price которых больше заданного\n"
                                    + "print_field_ascending_transport : вывести значения поля transport всех элементов в порядке возрастания");
                            break;
                        case "info":
                            colmanager.info(flats);
                            break;
                        case "show":
                            colmanager.show(flats);
                            break;
                        case "add":
                            colmanager.addFlat(flats);
                            break;
                        case "remove_head":
                            colmanager.removeHead(flats);
                            break;
                        case "clear":
                            colmanager.clear(flats);
                            break;
                        case "update":
                            colmanager.updateID(flats, Long.parseLong(commandArgs[0]));
                            break;
                        case "remove_by_id":
                            colmanager.removeID(flats, Long.parseLong(commandArgs[0]));
                            break;
                        case "remove_all_by_number_of rooms":
                            colmanager.removeByNumberOfRooms(flats, Long.parseLong(commandArgs[0]));
                            break;
                        case "filter_greater_than_price":
                            colmanager.priceFilter(flats, Integer.parseInt(commandArgs[0]));
                            break;
                        case "print_field_ascending_transport":
                            colmanager.transportOut(flats);
                            break;
                        case "add_if_max":
                            colmanager.addIfMax(flats);
                            break;
                        case "add_if_min":
                            colmanager.addIfMin(flats);
                            break;
                        case "save":
                            FileManager.writeToCsv(CollectionManager.filename, flats);
                            break;
                        case "exit":
                            System.exit(0);
                    }
                } else {
                    System.out.println("Неизвестная команда: " + commandName);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл скрипта не найден: " + filename);
        } catch (Exception e) {
            System.out.println("Ошибка выполнения скрипта: " + e.getMessage());
        }
    }
}

