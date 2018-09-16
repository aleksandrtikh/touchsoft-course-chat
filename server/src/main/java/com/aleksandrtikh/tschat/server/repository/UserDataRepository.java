package com.aleksandrtikh.tschat.server.repository;

import com.aleksandrtikh.tschat.server.model.User;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;


public class UserDataRepository {

    private static UserDataRepository ourInstance = new UserDataRepository();

    public static UserDataRepository getInstance() {
        return ourInstance;
    }

    private UserDataRepository() {}

    private ConcurrentHashMap<Session, User> existingUsers;
    private UserBooker userBooker;

    public UserBooker getUserBooker() {
        return userBooker;
    }

    public void setUserBooker(UserBooker userBooker) {
        this.userBooker = userBooker;
    }

    public ConcurrentHashMap<Session, User> getExistingUsers() {
        return existingUsers;
    }

    public void setExistingUsers(ConcurrentHashMap<Session, User> existingUsers) {
        this.existingUsers = existingUsers;
    }

    public User getUserBySession(Session session) {
        return existingUsers.get(session);
    }
}

