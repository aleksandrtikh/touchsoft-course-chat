package com.aleksandrtikh.tschat.server.model;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Chat {
    private final User agent;
    private final User customer;
    private final ExecutorService executor;

    public Chat(User agent, User customer) {
        this.agent = agent;
        this.customer = customer;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public User getAgent() {
        return agent;
    }

    public User getCustomer() {
        return customer;
    }

   public User getInterlocutor(User user) {
        if (user == agent) return customer;
        else if (user == customer) return agent;
        else return null;
    }

}