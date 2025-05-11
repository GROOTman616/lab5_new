import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        FileManager manager = new FileManager();
        manager.readFromCsv("C:\\Users\\79312\\OneDrive\\Рабочий стол\\Test1.csv");
//        String filename = args[0];
//        System.out.println(filename);
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Здравствуйте!");
//        PriorityQueue<Flat> flats = new PriorityQueue<>();
//        outerLoop:
//        while (true) {
//          System.out.print("Введите команду: ");
//          String[] answer = sc.nextLine().split(" ");
//          String command = answer[0];
//          switch (command) {
//            case "help":
//              System.out.println("СПРАВКА\n"
//                      + "help : вывести справку по доступным командам\n"
//                      + "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n"
//                      + "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n"
//                      + "add {element} : добавить новый элемент в коллекцию\n"
//                      + "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n"
//                      + "remove_by_id id : удалить элемент из коллекции по его id\n"
//                      + "clear : очистить коллекцию\n"
//                      + "save : сохранить коллекцию в файл\n"
//                      + "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n"
//                      + "exit : завершить программу (без сохранения в файл)\n"
//                      + "remove_head : вывести первый элемент коллекции и удалить его\n"
//                      + "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n"
//                      + "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n"
//                      + "remove_all_by_number_of_rooms numberOfRooms : удалить из коллекции все элементы, значение поля numberOfRooms которого эквивалентно заданному\n"
//                      + "filter_greater_than_price price : вывести элементы, значение поля price которых больше заданного\n"
//                      + "print_field_ascending_transport : вывести значения поля transport всех элементов в порядке возрастания");
//              break;
//          case "exit":
//              break outerLoop;
//          default:
//              System.out.println("Ошибка в названии команды! Введите повторно");
//          }
//        }
    }
}
