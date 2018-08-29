package com.aleksandrtikh.tschat.client;



import com.alelsandrtikh.tschat.*;
import javax.websocket.*;
import java.io.IOException;

@ClientEndpoint(encoders = MessageTextEncoder.class,
decoders = MessageTextDecoder.class)
public class ChatEndpoint {

    private Session userSession = null;

    public ChatEndpoint() {

    }

    @OnOpen
    public void onOpen(Session session) {
        this.userSession = session;
        System.out.println("Connected");
    }

    @OnMessage
    public void onMessage(Message message) {
        if (message.getType() == Message.MessageType.CONFIRMATION) {
            Client.isRegistered = true;
        } else {
            System.out.println(message.getContent());
        }
    }

    @OnError
    public void onError(Throwable exception){
        System.out.println("Error in socket");
        try {
            throw  exception;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            this.userSession.getBasicRemote().sendObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }
}
