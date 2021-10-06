package Exceptions;

public class INC_PASS extends Exception{

    public INC_PASS() {

        super("INC_PASS: The username or password entered was not found. Enter again!" + "|" +
                "Look if CAPS lock is on");
    }


}

