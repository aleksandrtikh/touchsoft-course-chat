package com.aleksandrtikh.tschat.server.controller;

import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.server.repository.ChatRepository;
import com.aleksandrtikh.tschat.server.repository.UserDataRepository;
import com.aleksandrtikh.tschat.server.service.ChatService;
import com.aleksandrtikh.tschat.server.service.UserService;
import com.aleksandrtikh.tschat.shared.ChatMessage;
import com.aleksandrtikh.tschat.shared.MessageTextDecoder;
import com.aleksandrtikh.tschat.shared.MessageTextEncoder;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ServerEndpoint(value = "/chat", encoders = MessageTextEncoder.class, decoders = MessageTextDecoder.class)
public class ChatServerEndpoint extends Endpoint {

    private static final ExecutorService freeUserExecutors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final Logger log = Logger.getLogger(ChatServerEndpoint.class);
    private UserDataRepository userDataRepository = UserDataRepository.getInstance();
    private ChatRepository chatRepository = ChatRepository.getInstance();
    private UserService userService = new UserService();
    private ChatService chatService = new ChatService();


    @OnMessage
    public void onMessage(Session session, ChatMessage message) {
        ExecutorService executor;
        User user = userDataRepository.getExistingUsers().get(session);
        if (user != null && userService.isUserInChat(user)) {
            executor = chatRepository.getChatByUser(user).getExecutor();
        } else executor = freeUserExecutors;
        Runnable messageHandler = new IncomingMessageHandler(session, message);
        executor.execute(messageHandler);
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {

        if (userDataRepository.getExistingUsers().containsKey(session)) {
            User user = userDataRepository.getExistingUsers().get(session);
            if (userService.isUserInChat(user)) {
                chatService.endChat(chatRepository.getChatByUser(user));
            }
            userService.unfreeUser(user);
            userService.unregisterUser(user);
        }
    }

    @OnError
    public void onError(Throwable error) {
        log.error(error);
    }
}
