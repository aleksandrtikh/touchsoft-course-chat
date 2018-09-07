package com.aleksandrtikh.tschat.server.controller;

import com.aleksandrtikh.tschat.server.command.*;
import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.shared.Message;

import javax.websocket.Session;

public class CommandParser {

    private final Session session;
    private final User user;
    private final Message message;
    private final User interlocutor;

    public CommandParser(Session session, User user, Message message, User interlocutor) {
        this.session = session;
        this.user = user;
        this.message = message;
        this.interlocutor = interlocutor;
    }


    private Command parseRegisterCommand() {
        if (message.getCommandPrefix().equalsIgnoreCase(RegisterCommand.COM_PREFIX)) {
            final String AGENT_ROLE_NAME = "AGENT";
            final String CUSTOMER_ROLE_NAME = "CUSTOMER";
            String[] args = message.getCommandArgs();
            User.Role userRole;
            switch (args[1].toUpperCase()) {
                case AGENT_ROLE_NAME:
                    userRole = User.Role.AGENT;
                    break;
                case CUSTOMER_ROLE_NAME:
                    userRole = User.Role.CUSTOMER;
                    break;
                default:
                    return new WrongCommand(session, message.getContent(), "wrong role");
            }
            String userName = args[2];
            return new RegisterCommand(new User(userName, session, userRole));
        } else {
            return new WrongCommand(session, message.getContent(), "unregistered user: use /register <role> <name>");
        }
    }

    public Command parse() {
        if (user == null) {
            return parseRegisterCommand();
        } else {
            if (message.isCommand()) {
                return parseMiscCommand();
            } else {
                return parseSendCommand();
            }
        }
    }

    private Command parseSendCommand() {
        if (interlocutor != null) return new SendCommand(interlocutor, message);
        else {
            switch (user.getRole()) {
                case CUSTOMER:
                    return new SaveMessageCommand(user, message);
                case AGENT:
                    return new WrongCommand(session, message.getContent(), "agent trying to send message without chat");
                default:
                    return null;
            }
        }
    }

    private Command parseMiscCommand() {
        switch (message.getCommandPrefix().toUpperCase()) {
            case LeaveCommand.COM_PREFIX: {
                if (interlocutor == null) {
                    return new WrongCommand(session, message.getContent(), "trying to leave without chat existing");
                } else if (user.getRole() == User.Role.AGENT) {
                    return new WrongCommand(session, message.getContent(), "agents can't leave chat");
                } else return new LeaveCommand(user);
            }
            case ExitCommand.COM_PREFIX:
                return new ExitCommand(user);
            default:
                return new WrongCommand(session, message.getContent(), "wrong command");
        }
    }
}
