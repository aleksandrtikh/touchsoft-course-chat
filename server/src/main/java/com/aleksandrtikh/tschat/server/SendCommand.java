package com.aleksandrtikh.tschat.server;

import com.aleksandrtikh.tschat.Message;

public class SendCommand implements Command {

    private final User interlocutor;

    private final Message message;


    public SendCommand(User interlocutor, Message message) {
        this.interlocutor = interlocutor;
        this.message = message;

    }

    public void execute() {
       interlocutor.send(message);
    }


}
