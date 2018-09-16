package com.aleksandrtikh.tschat.server.command;


import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.server.service.UserService;
import com.aleksandrtikh.tschat.shared.ChatMessage;

public class RegisterCommand implements Command {

    private UserService service = new UserService();
    private final User user;

    public void execute() {
        service.registerUser(user);
        ChatMessage confirmationMessage = new ChatMessage("successfully registered", ChatMessage.MessageType.CONFIRM, user.getUserName());
        user.send(confirmationMessage);
        if (user.getRole() == User.Role.AGENT) {
            service.freeUser(user);
        }
    }


    public RegisterCommand(User user) {
        this.user = user;
    }
}
