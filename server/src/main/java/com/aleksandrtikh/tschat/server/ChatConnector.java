package com.aleksandrtikh.tschat.server;


public class ChatConnector implements Runnable {

    public void run() {
        UserBooker booker = WebSocketServerRunner.getUserBooker();
        if (booker.hasFreePair()) {
            User agent = booker.bookNextAgent();
            User customer = booker.bookNextCustomer();
            Chat chat = new Chat(agent, customer);
            WebSocketServerRunner.getActiveChats().put(agent,chat);
            WebSocketServerRunner.getActiveChats().put(customer, chat);
            System.out.println("Chat started");
        }
    }
}
