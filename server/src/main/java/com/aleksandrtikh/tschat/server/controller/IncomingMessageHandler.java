package com.aleksandrtikh.tschat.server.controller;


import com.aleksandrtikh.tschat.server.command.Command;
import com.aleksandrtikh.tschat.server.model.Chat;
import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.server.repository.ChatRepository;
import com.aleksandrtikh.tschat.server.repository.UserDataRepository;
import com.aleksandrtikh.tschat.shared.ChatMessage;

import javax.websocket.Session;

public class IncomingMessageHandler implements Runnable {


    private final ChatMessage message;
    private final Session session;

    public void run() {
        User user = UserDataRepository.getInstance().getUserBySession(session);
        Chat chat = (user != null)
                ? ChatRepository.getInstance().getChatByUser(user)
                : null;
        User interlocutor = (chat != null) ? chat.getInterlocutor(user) : null;
        Command command = new CommandParser(session,user,message, interlocutor).parse();
        command.execute();
    }

    public IncomingMessageHandler(Session session, ChatMessage message ) {
        this.message = message;
        this.session = session;
    }
}
