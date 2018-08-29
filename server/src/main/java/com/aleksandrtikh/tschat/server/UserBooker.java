package com.aleksandrtikh.tschat.server;

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
