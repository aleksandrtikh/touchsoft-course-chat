package com.aleksandrtikh.tschat.server;


public class ExitCommand implements Command {
    public static final String COM_PREFIX = "/EXIT";
    private final User user;

    public void execute() {
            if (user.hasInterlocutor()) {
                user.getChat().end();
            }
            user.unfree();
            user.unregister();
            //TODO: log
            System.out.printf("user %s has left(exit)%n", user.getUserName());
    }


    public ExitCommand(User user) {
        this.user = user;
        }
}
