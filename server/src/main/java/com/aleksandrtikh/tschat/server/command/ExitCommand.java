package com.aleksandrtikh.tschat.server.command;


import com.aleksandrtikh.tschat.server.model.User;

public class ExitCommand implements Command {
    public static final String COM_PREFIX = "/EXIT";
    private final User user;

    public void execute() {
            if (user.hasInterlocutor()) {
                user.getChat().end();
            }
            user.unfree();
            user.unregister();
    }


    public ExitCommand(User user) {
        this.user = user;
        }
}
