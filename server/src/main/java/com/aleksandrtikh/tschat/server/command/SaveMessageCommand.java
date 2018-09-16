package com.aleksandrtikh.tschat.server.command;

import com.aleksandrtikh.tschat.server.service.UserService;
import com.aleksandrtikh.tschat.shared.ChatMessage;
import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.server.repository.UserDataRepository;

public class SaveMessageCommand implements Command {
    private final User user;
    private final ChatMessage message;
    private UserService service = new UserService();

    public void execute() {
            user.getMessageBuffer().offer(message);
            if (!UserDataRepository.getInstance().getUserBooker().isUserFree(user)) {
                service.freeUser(user);
            }
    }

    public SaveMessageCommand(User user, ChatMessage message) {
        this.user = user;
        this.message = message;
    }
}
