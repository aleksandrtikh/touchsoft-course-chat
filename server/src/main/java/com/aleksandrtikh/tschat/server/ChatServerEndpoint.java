package com.aleksandrtikh.tschat.server;

import com.aleksandrtikh.tschat.Message;
import com.aleksandrtikh.tschat.MessageTextDecoder;
import com.aleksandrtikh.tschat.MessageTextEncoder;
import org.apache.log4j.Logger;
import javax.websocket.*;
import javax.websocket.server.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ServerEndpoint(value = "/coursechat", encoders = MessageTextEncoder.class, decoders = MessageTextDecoder.class)
public class ChatServerEndpoint extends Endpoint {

    private static final ExecutorService freeUserExecutors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final Logger log = Logger.getLogger(ChatServerEndpoint.class);

    @OnMessage
    public void onMessage(Session session, Message message) {
        ExecutorService executor;
        User user = UserDataRepository.getExistingUsers().get(session);
        if (user != null && user.hasInterlocutor()) {
            executor = user.getChat().getExecutor();
        } else executor = freeUserExecutors;
        Runnable messageHandler = new IncomingMessageHandler(session,message);
        executor.execute(messageHandler);
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        if (UserDataRepository.getExistingUsers().containsKey(session)) {
            User user = UserDataRepository.getExistingUsers().get(session);
            if (user.hasInterlocutor()) {
                user.getChat().end();
            }
            user.unfree();
            user.unregister();
        }
    }

    @OnError
    public void onError(Throwable error) {
        log.error(error);
    }
}
