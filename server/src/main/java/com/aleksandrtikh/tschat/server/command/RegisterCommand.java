package com.aleksandrtikh.tschat.server.command;


import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.shared.Message;

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
