package com.aleksandrtikh.tschat.server;

import com.alelsandrtikh.tschat.Message;

public class SaveMessageCommand implements Command {
    private final User user;
    private final Message message;

    public void execute() {
            user.getMessageBuffer().offer(message);
            //TODO: log
            System.out.println("message saved");
    }

    public SaveMessageCommand(User user, Message message) {
        this.user = user;
        this.message = message;
    }
}
