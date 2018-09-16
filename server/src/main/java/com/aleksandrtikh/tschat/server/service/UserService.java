package com.aleksandrtikh.tschat.server.service;

import com.aleksandrtikh.tschat.server.repository.UserBooker;
import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.server.repository.ChatRepository;
import com.aleksandrtikh.tschat.server.repository.UserDataRepository;
import org.apache.log4j.Logger;

public class UserService {

    private UserDataRepository userDataRepository = UserDataRepository.getInstance();
    private ChatRepository chatRepository = ChatRepository.getInstance();
    private static final Logger log = Logger.getLogger(UserService.class);


    public void registerUser(User user) {
        userDataRepository.getExistingUsers().put(user.getSession(), user);
        log.info(String.format("%s %s logged in.", user.getRole(), user.getUserName()));
    }

    public void unregisterUser(User user) {
        userDataRepository.getExistingUsers().remove(user.getSession(), user);
        log.info(String.format("%s %s logged out.", user.getRole(), user.getUserName()));
    }
    public void freeUser(User user) {
        UserBooker booker = userDataRepository.getUserBooker();
        switch (user.getRole()) {
            case CUSTOMER:
                booker.freeCustomer(user);
                break;
            case AGENT:
                booker.freeAgent(user);
                break;
            default:
                throw new IllegalArgumentException("User has an unknown role");
        }
    }

    public void unfreeUser(User user) {
        userDataRepository.getUserBooker().bookUser(user);
    }

    public boolean isUserInChat(User user) {
        return chatRepository.getAll().containsKey(user);
    }

    public User getInterlocutor(User user) {
        return chatRepository.getChatByUser(user).getInterlocutor(user);
    }

}
