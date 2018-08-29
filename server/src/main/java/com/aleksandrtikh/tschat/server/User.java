package com.aleksandrtikh.tschat.server;

import com.alelsandrtikh.tschat.Message;

import javax.websocket.Session;
import java.util.LinkedList;

public class User {

	private String userName;
	private Role role;
	private LinkedList<Message> messageBuffer;
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


    public LinkedList<Message> getMessageBuffer() {
        return messageBuffer;
    }

	public Session getSession() {
		return session;
	}

    public boolean hasInterlocutor() {
	    return WebSocketServerRunner.getActiveChats().containsKey(this);
    }

    public boolean hasSavedMessages() {
	    return (messageBuffer != null) && !messageBuffer.isEmpty();
    }

	public Chat getChat() {
		return WebSocketServerRunner.getActiveChats().get(this);
	}

    public void unfree() {
	    WebSocketServerRunner.getUserBooker().bookUser(this);
    }

	public void unregister() {
	    WebSocketServerRunner.getExistingUsers().remove(this.session,this);
	}

	public User getInterlocutor() {
	    Chat chat = this.getChat();
        switch (this.role) {
            case CUSTOMER: return chat.getAgent();
            case AGENT: return chat.getCustomer();
            default: throw new IllegalArgumentException("User has an unknown role");
        }
	}

    public void register() {
	    WebSocketServerRunner.getExistingUsers().put(this.session, this);
    }

    public void free() {
        UserBooker booker = WebSocketServerRunner.getUserBooker();
        switch (this.role) {
            case CUSTOMER: booker.freeCustomer(this);
                break;
            case AGENT: booker.freeAgent(this);
                break;
                default: throw new IllegalArgumentException("User has an unknown role");
        }
    }

    public boolean tryFindInterlocutor() {
	    if (this.role == Role.CUSTOMER && !WebSocketServerRunner.getUserBooker().isUserFree(this)) {
	        this.free();
	        return this.hasInterlocutor();
        } else return false;
    }


    public enum Role {
        CUSTOMER, AGENT
    }

}
