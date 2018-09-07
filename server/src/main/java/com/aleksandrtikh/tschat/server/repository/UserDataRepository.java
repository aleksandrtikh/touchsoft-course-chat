package com.aleksandrtikh.tschat.server.repository;

import com.aleksandrtikh.tschat.server.UserBooker;
import com.aleksandrtikh.tschat.server.model.Chat;
import com.aleksandrtikh.tschat.server.model.User;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataRepository {


    private static ConcurrentHashMap<User, Chat> activeChats;
    private static ConcurrentHashMap<Session, User> existingUsers;
    private static UserBooker userBooker;

    public static UserBooker getUserBooker() {
        return userBooker;
    }

    public static void setUserBooker(UserBooker userBooker) {
        UserDataRepository.userBooker = userBooker;
    }

    public static ConcurrentHashMap<User, Chat> getActiveChats() {
        return activeChats;
    }

    public static void setActiveChats(ConcurrentHashMap<User, Chat> activeChats) {
        UserDataRepository.activeChats = activeChats;
    }

    public static ConcurrentHashMap<Session, User> getExistingUsers() {
        return existingUsers;
    }

    public static void setExistingUsers(ConcurrentHashMap<Session, User> existingUsers) {
        UserDataRepository.existingUsers = existingUsers;
    }
}

