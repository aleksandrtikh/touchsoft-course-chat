package com.aleksandrtikh.tschat.server;



public class LeaveCommand implements Command {

    private final User user;
    public static final String COM_PREFIX = "/LEAVE";

    public void execute() {
        user.getChat().end();
        //TODO: log
        System.out.printf("user %s has left(leave)%n", user.getUserName());
    }


    public LeaveCommand(User user) {
        this.user = user;
    }
}
