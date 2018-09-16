package com.aleksandrtikh.tschat.server.model;


import com.aleksandrtikh.tschat.shared.ChatMessage;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.LinkedList;

public class User {

    private static final Logger log = Logger.getLogger(User.class);
    private String userName;
    private Role role;
    private LinkedList<ChatMessage> messageBuffer;
    private Session session;

    public String getUserName() {
        return userName;
    }


    public Role getRole() {
        return role;
    }

    public User(String userName, Session session, Role role) {
        this.userName = userName;
        this.session = session;

        this.role = role;
        if (role == Role.CUSTOMER) {
            messageBuffer = new LinkedList<>();
        }
    }

    public LinkedList<ChatMessage> getMessageBuffer() {
        return messageBuffer;
    }

    public Session getSession() {
        return session;
    }

    public boolean hasSavedMessages() {
        return (messageBuffer != null) && !messageBuffer.isEmpty();
    }

    public void send(ChatMessage message) {
        try {
            this.session.getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException e) {
            log.error(e);
        }
    }

    public enum Role {
        CUSTOMER, AGENT
    }

}
