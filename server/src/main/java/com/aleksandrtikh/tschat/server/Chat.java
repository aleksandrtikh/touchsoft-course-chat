package com.aleksandrtikh.tschat.server;


import com.aleksandrtikh.tschat.Message;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.*;


public class Chat {
    private final User agent;
    private final User customer;
    private final ExecutorService executor;
    private static final Logger log = Logger.getLogger(Chat.class);

    public void end() {
        UserDataRepository.getActiveChats().remove(agent, this);
        UserDataRepository.getActiveChats().remove(customer, this);
        agent.free();
        log.info(String.format("Chat ended between agent %s and customer %s.", agent.getUserName(), customer.getUserName()));
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

    public User getInterlocutor(User user) {
        if (user == agent) return customer;
        else if (user == customer) return agent;
        else return null;
    }

    public void begin() {
        UserDataRepository.getActiveChats().put(agent, this);
        UserDataRepository.getActiveChats().put(customer, this);
        if (customer.hasSavedMessages()) {
            LinkedList<Message> buffer = customer.getMessageBuffer();
            while (!buffer.isEmpty()) {
                agent.send(buffer.poll());
            }
        }
        log.info(String.format("Chat started between agent %s and customer %s.", agent.getUserName(), customer.getUserName()));
    }
}