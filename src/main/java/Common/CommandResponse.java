package Common;

import java.io.Serializable;

public class CommandResponse implements Serializable {
    private final boolean success;
    private final String message;

    public CommandResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }
}
