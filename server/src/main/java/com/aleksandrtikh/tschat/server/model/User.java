package com.aleksandrtikh.tschat.server.model;



import com.aleksandrtikh.tschat.shared.Message;
import com.aleksandrtikh.tschat.server.UserBooker;
import com.aleksandrtikh.tschat.server.repository.UserDataRepository;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.LinkedList;

public class User {

    private static final Logger log = Logger.getLogger(User.class);

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
	    return UserDataRepository.getActiveChats().containsKey(this);
    }

    public boolean hasSavedMessages() {
	    return (messageBuffer != null) && !messageBuffer.isEmpty();
    }

	public Chat getChat() {
		return UserDataRepository.getActiveChats().get(this);
	}

    public void unfree() {
	    UserDataRepository.getUserBooker().bookUser(this);
    }

	public void unregister() {
	    UserDataRepository.getExistingUsers().remove(this.session,this);
        log.info(String.format("%s %s logged out.", role,userName));
	}

	public User getInterlocutor() {
	    Chat chat = this.getChat();
        return chat.getInterlocutor(this);
	}

    public void register() {
		UserDataRepository.getExistingUsers().put(this.session, this);
        log.info(String.format("%s %s logged in.", role,userName));
    }

    public void free() {
        UserBooker booker = UserDataRepository.getUserBooker();
        switch (this.role) {
            case CUSTOMER: booker.freeCustomer(this);
                break;
            case AGENT: booker.freeAgent(this);
                break;
                default: throw new IllegalArgumentException("User has an unknown role");
        }
    }

    public void send(Message message) {
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
