package Commands;

import Common.CommandResponse;
import Common.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public interface Command extends Serializable {
    CommandResponse execute(Object[] args, Object data, User user) throws IOException;
    String getName();
    String getDescription();
}