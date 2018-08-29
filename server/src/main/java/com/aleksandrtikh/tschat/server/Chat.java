package com.aleksandrtikh.tschat.server;


import java.util.concurrent.*;

public class Chat {
    private User agent;
    private User customer;
    private ExecutorService executor;

    public void end() {
        WebSocketServerRunner.getActiveChats().remove(agent, this);
        WebSocketServerRunner.getActiveChats().remove(customer, this);
        agent.free();
    }

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

    private User getInterlocutor(User user) {
        if (user == agent) return customer;
        else if (user == customer) return agent;
        else return null;
    }
}