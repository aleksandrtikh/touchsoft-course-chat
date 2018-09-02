package com.aleksandrtikh.tschat.server;


public class ChatConnector implements Runnable {


    public void run() {
        UserBooker booker = UserDataRepository.getUserBooker();
        if (booker.hasFreePair()) {
            User agent = booker.bookNextAgent();
            User customer = booker.bookNextCustomer();
            Chat chat = new Chat(agent, customer);
            chat.begin();
        }
    }
}
