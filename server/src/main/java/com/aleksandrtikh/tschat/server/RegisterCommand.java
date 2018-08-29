package com.aleksandrtikh.tschat.server;

import com.alelsandrtikh.tschat.Message;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

public class RegisterCommand implements Command {
    public static final String COM_PREFIX = "/REGISTER";
    private final Session session;
    private String userName;
    private User.Role role;

    public static Command parse(Session session, String[] args) {
        User.Role userRole;
        final String AGENT_ROLE_NAME = "AGENT";
        final String CUSTOMER_ROLE_NAME = "CUSTOMER";
        String userName = args[2];
        switch (args[1].toUpperCase()) {
            case AGENT_ROLE_NAME:
                userRole = User.Role.AGENT;
                break;
            case CUSTOMER_ROLE_NAME:
                userRole = User.Role.CUSTOMER;
                break;
            default:
                String errorMessage = String.format("%s %s %s", COM_PREFIX, args[1], args[2]);
                return new WrongCommand(session, new Message(errorMessage, Message.MessageType.ERROR), "wrong role");
        }
        return new RegisterCommand(session, userName, userRole);
    }


    public void execute() {
        User user = new User(userName, session, role);
        user.register();
        try {
            Message confirmationMessage = new Message("successfully registered", Message.MessageType.CONFIRMATION);
            user.getSession().getBasicRemote().sendObject(confirmationMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
        if (user.getRole() == User.Role.AGENT) {
            user.free();
        }
        //TODO: Log
        System.out.println("User registered as " + role.toString());
    }


    public RegisterCommand(Session session, String userName, User.Role role) {
        this.session = session;
        this.userName = userName;
        this.role = role;
    }
}
