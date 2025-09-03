package Common;

import Commands.Command;

import java.io.Serializable;

public class CommandRequest implements Serializable {
    private String commandName;
    private Object[] args;
    private Object data;

    public CommandRequest(String commandName, Object[] args, Object data) {
        this.commandName = commandName;
        this.args = args;
        this.data=data;
    }

    public String getCommandName(){
        return commandName;
    }

    public Object[] getArgs(){
        return args;
    }

    public Object getData() {
        return data;
    }
}
