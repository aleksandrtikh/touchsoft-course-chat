package com.aleksandrtikh.tschat.server;

import com.aleksandrtikh.tschat.Message;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

public class WrongCommand implements Command {
    private static Logger log = Logger.getLogger(WrongCommand.class);

    private final String message;
    private final String cause;
    private final Session session;

    public void execute() {
        String errorMessage = String.format("Command syntax error: %s in command: %s%n", cause, message);
        Message error = new Message(errorMessage, Message.MessageType.ERROR, WebSocketServerRunner.SERVER_NAME);
        try {
            session.getBasicRemote().sendObject(error);
        } catch (IOException | EncodeException e) {
           log.error(e);
        }
    }

    public WrongCommand(Session session, String message, String cause) {
        this.message = message;
        this.session = session;
        this.cause = cause;
    }
}
