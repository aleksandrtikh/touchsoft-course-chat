package com.aleksandrtikh.tschat.server.repository;

import com.aleksandrtikh.tschat.server.model.User;

public interface UserBooker {

    boolean isUserFree(User user);
    void freeAgent(User user);
    void freeCustomer(User user);
    boolean bookUser(User user);
    User bookNextAgent();
    User bookNextCustomer();
    boolean hasFreePair();
    void addFreeEventListener(Runnable listener);

    void removeFreeEventListener(Runnable listener);
}
