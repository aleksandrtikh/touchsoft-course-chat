package com.aleksandrtikh.tschat.server.controller;


import com.aleksandrtikh.tschat.server.UserBooker;
import com.aleksandrtikh.tschat.server.model.Chat;
import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.server.repository.UserDataRepository;

public class ChatConnector implements Runnable {


    public void run() {
        UserBooker booker = UserDataRepository.getInstance().getUserBooker();
        if (booker.hasFreePair()) {
            User agent = booker.bookNextAgent();
            User customer = booker.bookNextCustomer();
            Chat chat = new Chat(agent, customer);
            chat.begin();
        }
    }
}
