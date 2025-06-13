package Commands;

import java.io.IOException;
import java.util.Scanner;

public interface Command {
    void execute(String[] args, Scanner scanner) throws IOException;
    String getName();
    String getDescription();
}