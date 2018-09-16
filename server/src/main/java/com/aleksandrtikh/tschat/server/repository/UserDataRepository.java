package com.aleksandrtikh.tschat.server.repository;

import com.aleksandrtikh.tschat.server.UserBooker;
import com.aleksandrtikh.tschat.server.model.Chat;
import com.aleksandrtikh.tschat.server.model.User;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;


public class UserDataRepository {

    private static UserDataRepository instance;

    public static UserDataRepository getInstance() {
        if (instance == null) {
            instance = new UserDataRepository();
        }
        return instance;
    }

    private ConcurrentHashMap<User, Chat> activeChats;
    private ConcurrentHashMap<Session, User> existingUsers;
    private UserBooker userBooker;

    public UserBooker getUserBooker() {
        return userBooker;
    }

    public void setUserBooker(UserBooker userBooker) {
        this.userBooker = userBooker;
    }

    public ConcurrentHashMap<User, Chat> getActiveChats() {
        return activeChats;
    }

    public void setActiveChats(ConcurrentHashMap<User, Chat> activeChats) {
        this.activeChats = activeChats;
    }

    public ConcurrentHashMap<Session, User> getExistingUsers() {
        return existingUsers;
    }

    public void setExistingUsers(ConcurrentHashMap<Session, User> existingUsers) {
        this.existingUsers = existingUsers;
    }
}

