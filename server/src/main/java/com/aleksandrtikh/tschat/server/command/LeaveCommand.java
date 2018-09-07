package com.aleksandrtikh.tschat.server.command;


import com.aleksandrtikh.tschat.server.model.User;

public class LeaveCommand implements Command {

    private final User user;
    public static final String COM_PREFIX = "/LEAVE";

    public void execute() {
        user.getChat().end();
    }


    public LeaveCommand(User user) {
        this.user = user;
    }
}
