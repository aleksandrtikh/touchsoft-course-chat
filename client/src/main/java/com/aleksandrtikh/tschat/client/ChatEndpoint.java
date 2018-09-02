package com.aleksandrtikh.tschat.client;



import com.aleksandrtikh.tschat.Message;
import com.aleksandrtikh.tschat.MessageTextDecoder;
import com.aleksandrtikh.tschat.MessageTextEncoder;
import org.apache.log4j.Logger;

import javax.websocket.*;
import java.io.IOException;



@ClientEndpoint(encoders = MessageTextEncoder.class,
decoders = MessageTextDecoder.class)
public class ChatEndpoint {

    private static final Logger log = Logger.getLogger(ChatEndpoint.class);
    private Session userSession = null;

    public ChatEndpoint() {

    }

    @OnOpen
    public void onOpen(Session session) {
        this.userSession = session;
    }

    @OnMessage
    public void onMessage(Message message) {
        if (message.getType() == Message.MessageType.CONFIRMATION) {
            Client.isRegistered = true;
            Client.username = message.getUsername();
            System.out.printf("Successfully registered as.%s%n", message.getUsername());
        } else {
            System.out.printf("%s: %s%n", message.getUsername(), message.getContent());
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        if (reason.getCloseCode() != CloseReason.CloseCodes.NORMAL_CLOSURE) {
            System.out.println("Something went wrong, connection closed.");
            Client.isWorking = false;
        }
    }

    @OnError
    public void onError(Throwable e){
        System.out.println("An error has occurred, check log for details. \nThe application will be closed now.");
        log.error(e);
        System.exit(1);
    }

    public void sendMessage(Message message) {
        try {
            this.userSession.getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }
}
