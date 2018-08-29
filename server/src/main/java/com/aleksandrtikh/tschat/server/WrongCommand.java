package com.aleksandrtikh.tschat.server;

import com.alelsandrtikh.tschat.Message;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

public class WrongCommand implements Command {
    private Message message;
    private String cause;
    private Session session;

    public void execute() {
        String errorMessage = String.format("Command syntax error: %s in command: %s%n", cause, message.getContent());
        Message error = new Message(errorMessage, Message.MessageType.ERROR);
        try {
            session.getBasicRemote().sendObject(error);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
        // TODO: log
            System.out.print(errorMessage);

    }

    public WrongCommand(Session session, Message message, String cause) {
        this.message = message;
        this.session = session;
        this.cause = cause;
    }
}
