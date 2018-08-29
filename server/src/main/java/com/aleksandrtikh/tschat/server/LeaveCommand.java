package com.aleksandrtikh.tschat.server;

import com.alelsandrtikh.tschat.Message;

public class LeaveCommand implements Command {

    private User user;
    private  Chat chat;
    public static final String COM_PREFIX = "/LEAVE";
    private static final boolean allowAgents = false;

    public static Command parse(User user) {
        if (!user.hasInterlocutor()) {
            return new WrongCommand(user.getSession(), new Message(COM_PREFIX, Message.MessageType.ERROR), "trying to leave without chat existing" );
        } else if (user.getRole() == User.Role.AGENT && !allowAgents) {
            return new WrongCommand(user.getSession(), new Message(COM_PREFIX, Message.MessageType.ERROR), "agents can't leave chat");
        } else return new LeaveCommand(user);
    }

    public void execute() {
        user.getChat().end();
        //TODO: log
        System.out.printf("user %s has left(leave)%n", user.getUserName());
    }


    public LeaveCommand(User user) {
        this.user = user;
    }
}
