package com.aleksandrtikh.tschat.server;

import javax.websocket.CloseReason;
import java.io.IOException;

public class ExitCommand implements Command {
    public static final String COM_PREFIX = "/EXIT";
    private User user;

    public void execute() {
            if (user.hasInterlocutor()) {
                user.getChat().end();
            }
            user.unfree();
            user.unregister();
            //TODO: log
            System.out.printf("user %s has left(exit)%n", user.getUserName());
        try {
            user.getSession().close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "user disconnected"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ExitCommand(User user) {
        this.user = user;
        }
}
