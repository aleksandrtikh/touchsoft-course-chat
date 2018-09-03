package com.aleksandrtikh.tschat.server;


import com.aleksandrtikh.tschat.Message;
import javax.websocket.Session;

public class IncomingMessageHandler implements Runnable {


    private final Message message;
    private final Session session;

    public void run() {
        User user = UserDataRepository.getExistingUsers().get(session);
        Chat chat = (user != null)
                ? UserDataRepository.getActiveChats().get(user)
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
