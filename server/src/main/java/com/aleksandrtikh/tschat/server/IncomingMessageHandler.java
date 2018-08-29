package com.aleksandrtikh.tschat.server;


import com.alelsandrtikh.tschat.Message;
import javax.websocket.Session;

public class IncomingMessageHandler implements Runnable {


    Message message;
    Session session;

    public void run() {
        Command command = parseCommand(message, WebSocketServerRunner.getExistingUsers().get(session));
        command.execute();
    }

    private Command parseCommand(Message message, User user) {
        if (user == null) {
            if (message.getCommandPrefix().equalsIgnoreCase(RegisterCommand.COM_PREFIX)) {
                return RegisterCommand.parse(session, message.getCommandArgs());
            } else {
                return new WrongCommand(session, message, "unregistered user: use /register <role> <name>");
            }
        } else {
            if (message.isCommand()) {
                switch (message.getCommandPrefix().toUpperCase()) {
                    case LeaveCommand.COM_PREFIX:
                        return LeaveCommand.parse(user);
                    case ExitCommand.COM_PREFIX:
                        return new ExitCommand(user);
                    default:
                        return new WrongCommand(session, message, "wrong command");
                }
            } else return SendCommand.parse(user, message);
        }
    }

    public IncomingMessageHandler(Session session, Message message ) {
        this.message = message;
        this.session = session;
    }
}
