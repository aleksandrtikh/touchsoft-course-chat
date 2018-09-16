package com.aleksandrtikh.tschat.server.controller;


import com.aleksandrtikh.tschat.shared.Message;
import com.aleksandrtikh.tschat.server.command.Command;
import com.aleksandrtikh.tschat.server.model.Chat;
import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.server.repository.UserDataRepository;

import javax.websocket.Session;

public class IncomingMessageHandler implements Runnable {


    private final Message message;
    private final Session session;

    public void run() {
        UserDataRepository repository = UserDataRepository.getInstance();
        User user = repository.getExistingUsers().get(session);
        Chat chat = (user != null)
                ? repository.getActiveChats().get(user)
                : null;
        User interlocutor = (chat != null) ? chat.getInterlocutor(user) : null;
        Command command = new CommandParser(session,user,message, interlocutor).parse();
        command.execute();
    }

    public IncomingMessageHandler(Session session, Message message ) {
        this.message = message;
        this.session = session;
    }
}
