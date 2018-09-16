package com.aleksandrtikh.tschat.server.repository;

import com.aleksandrtikh.tschat.server.model.Chat;
import com.aleksandrtikh.tschat.server.model.User;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRepository {
    private static ChatRepository ourInstance = new ChatRepository();

    public static ChatRepository getInstance() {
        return ourInstance;
    }

    private ChatRepository() {}
    private ConcurrentHashMap<User, Chat> activeChats;

    public Chat getChatByUser(User user) {
        return activeChats.get(user);
    }

    public ConcurrentHashMap<User,Chat> getAll() {
        return activeChats;
    }

    public void setAll(ConcurrentHashMap<User, Chat> value) {
        activeChats = value;
    }
}
