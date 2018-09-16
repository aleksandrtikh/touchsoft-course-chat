package com.aleksandrtikh.tschat.server.controller;

import com.aleksandrtikh.tschat.server.command.*;
import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.shared.ChatMessage;

import javax.websocket.Session;

public class CommandParser {

    private final Session session;
    private final User user;
    private final ChatMessage message;
    private final User interlocutor;

    public CommandParser(Session session, User user, ChatMessage message, User interlocutor) {
        this.session = session;
        this.user = user;
        this.message = message;
        this.interlocutor = interlocutor;
    }


    private Command parseRegisterCommand() {
        if (message.getType() == ChatMessage.MessageType.REGISTER) {
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
                    return new WrongCommand(session, message.getContent(), WrongCommand.Cause.WRONG_USER_ROLE);
            }
            String userName = args[2];
            return new RegisterCommand(new User(userName, session, userRole));
        } else {
            return new WrongCommand(session, message.getContent(), WrongCommand.Cause.UNREGISTERED);
        }
    }

    public Command parse() {
        if (user == null) {
            return parseRegisterCommand();
        } else {
            switch (message.getType()) {
                case SEND:
                    return parseSendCommand();
                case LEAVE:
                    return parseLeaveCommand();
                case EXIT:
                    return parseExitCommand();
                default: return new WrongCommand(user.getSession(), message.getContent(), WrongCommand.Cause.WRONG_MESSAGE_TYPE);
            }
        }
    }

    private Command parseExitCommand() {
        return new ExitCommand(user);
    }

    private Command parseSendCommand() {
        if (interlocutor != null) return new SendCommand(interlocutor, message);
        else {
            switch (user.getRole()) {
                case CUSTOMER:
                    return new SaveMessageCommand(user, message);
                case AGENT:
                    return new WrongCommand(user.getSession(), message.getContent(), WrongCommand.Cause.NO_CHAT);
                default:
                    return null;
            }
        }
    }


    private Command parseLeaveCommand() {
        if (interlocutor == null) {
            return new WrongCommand(user.getSession(), message.getContent(), WrongCommand.Cause.NO_CHAT);
        } else if (user.getRole() == User.Role.AGENT) {
            return new WrongCommand(user.getSession(), message.getContent(), WrongCommand.Cause.ACTION_DENIED);
        } else return new LeaveCommand(user);
    }
}
