package com.aleksandrtikh.tschat.server.command;

import com.aleksandrtikh.tschat.shared.ChatMessage;
import com.aleksandrtikh.tschat.server.WebSocketServerRunner;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

public class WrongCommand implements Command {
    private static Logger log = Logger.getLogger(WrongCommand.class);

    private final String message;
    private final Cause cause;
    private final Session session;

    public void execute() {
        String errorMessage = String.format("ChatMessage error: %s in command: %s", cause.getErrorMessage(), message);
        ChatMessage error = new ChatMessage(errorMessage, ChatMessage.MessageType.ERROR, WebSocketServerRunner.SERVER_NAME);
        try {
            session.getBasicRemote().sendObject(error);
        } catch (IOException | EncodeException e) {
           log.error(e);
        }
    }

    public WrongCommand(Session session, String message, Cause cause) {
        this.message = message;
        this.session = session;
        this.cause = cause;
    }

    public enum Cause {
        WRONG_USER_ROLE, UNREGISTERED, WRONG_MESSAGE_TYPE, NO_CHAT, ACTION_DENIED;

        public String getErrorMessage() {
            switch (this) {
                case WRONG_USER_ROLE: return "nonexistent role";
                case UNREGISTERED: return "unregistered user: use /register <role> <name>";
                case WRONG_MESSAGE_TYPE: return "wrong message type: something's wrong with your client or our server";
                case NO_CHAT: return "you must be a member of a chat to perform that action";
                case ACTION_DENIED: return "action is not allowed for your current role";
                default: return null;
            }
        }
        }

}
