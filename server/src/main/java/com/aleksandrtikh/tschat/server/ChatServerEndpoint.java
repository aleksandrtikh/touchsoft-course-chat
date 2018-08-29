package com.aleksandrtikh.tschat.server;

import com.alelsandrtikh.tschat.*;
import javax.websocket.*;
import javax.websocket.server.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ServerEndpoint(value = "/coursechat", encoders = MessageTextEncoder.class, decoders = MessageTextDecoder.class)
public class ChatServerEndpoint extends Endpoint {

    private static ExecutorService freeUserExecutors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());



    @OnMessage
    public void onMessage(Session session, Message message) {
        System.out.println("Message received");
        ExecutorService executor;
        User user = WebSocketServerRunner.getExistingUsers().get(session);
        if (user != null && user.hasInterlocutor()) {
            executor = user.getChat().getExecutor();
        } else executor = freeUserExecutors;
        Runnable messageHandler = new IncomingMessageHandler(session,message);
        executor.execute(messageHandler);
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        System.out.println("Connection opened");

    }
}
