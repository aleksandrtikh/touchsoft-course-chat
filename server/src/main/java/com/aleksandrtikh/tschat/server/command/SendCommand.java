package com.aleksandrtikh.tschat.server.command;

import com.aleksandrtikh.tschat.shared.ChatMessage;
import com.aleksandrtikh.tschat.server.model.User;

public class SendCommand implements Command {

    private final User interlocutor;

    private final ChatMessage message;


    public SendCommand(User interlocutor, ChatMessage message) {
        this.interlocutor = interlocutor;
        this.message = message;

    }

    public void execute() {
       interlocutor.send(message);
    }


}
