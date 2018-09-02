package com.aleksandrtikh.tschat.server;

import com.aleksandrtikh.tschat.Message;

public class SaveMessageCommand implements Command {
    private final User user;
    private final Message message;

    public void execute() {
            user.getMessageBuffer().offer(message);
            if (!UserDataRepository.getUserBooker().isUserFree(user)) {
                user.free();
            }
    }

    public SaveMessageCommand(User user, Message message) {
        this.user = user;
        this.message = message;
    }
}
