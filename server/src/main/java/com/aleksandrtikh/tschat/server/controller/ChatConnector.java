package com.aleksandrtikh.tschat.server.controller;


import com.aleksandrtikh.tschat.server.repository.UserBooker;
import com.aleksandrtikh.tschat.server.model.Chat;
import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.server.repository.UserDataRepository;
import com.aleksandrtikh.tschat.server.service.ChatService;

public class ChatConnector implements Runnable {

    private ChatService chatService;

    public ChatConnector(ChatService chatService) {
        this.chatService = chatService;
    }

    public void run() {
        UserBooker booker = UserDataRepository.getInstance().getUserBooker();
        if (booker.hasFreePair()) {
            User agent = booker.bookNextAgent();
            User customer = booker.bookNextCustomer();
            Chat chat = new Chat(agent, customer);
            chatService.beginChat(chat);
        }
    }
}
