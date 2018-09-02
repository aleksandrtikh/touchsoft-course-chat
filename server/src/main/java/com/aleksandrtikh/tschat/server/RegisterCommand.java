package com.aleksandrtikh.tschat.server;

import com.aleksandrtikh.tschat.Message;

public class RegisterCommand implements Command {
    public static final String COM_PREFIX = "/REGISTER";
    private final User user;

    public void execute() {
        user.register();
        Message confirmationMessage = new Message("successfully registered", Message.MessageType.CONFIRMATION, user.getUserName());
        user.send(confirmationMessage);
        if (user.getRole() == User.Role.AGENT) {
            user.free();
        }
    }


    public RegisterCommand(User user) {
        this.user = user;
    }
}
