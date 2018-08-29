package com.aleksandrtikh.tschat.server;

import com.alelsandrtikh.tschat.Message;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.LinkedList;

public class SendCommand implements Command {

    private User interlocutor;
    private User user;
    private Message message;


    public SendCommand(User user, User interlocutor, Message message) {
        this.user = user;
        this.interlocutor = interlocutor;
        this.message = message;

    }

    public static Command parse(User user, Message message) {
        if (user.hasInterlocutor() || user.tryFindInterlocutor()) {
            User interlocutor = user.getInterlocutor();
            return new SendCommand(user, interlocutor, message);
        } else {
            switch (user.getRole()) {
                case CUSTOMER:
                    return new SaveMessageCommand(user, message);
                case AGENT:
                    return new WrongCommand(user.getSession(), message, "agent trying to send message without chat");
                default:
                    return null;
            }
        }
    }

    public void execute() {
        try {
            if (user.hasSavedMessages()) {
                LinkedList<Message> buffer = user.getMessageBuffer();
                while (!buffer.isEmpty()) {
                    interlocutor.getSession()
                            .getBasicRemote()
                            .sendObject(buffer.poll());
                }
            }
            interlocutor.getSession()
                    .getBasicRemote()
                    .sendObject(message);
            //TODO: log
            System.out.println("message sent");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }


}
