package com.aleksandrtikh.tschat.server;


import com.aleksandrtikh.tschat.Message;
import javax.websocket.Session;

public class IncomingMessageHandler implements Runnable {


    private final Message message;
    private final Session session;

    public void run() {
        User user = UserDataRepository.getExistingUsers().get(session);
        Chat chat = (user != null)
                ? UserDataRepository.getActiveChats().get(user)
                : null;
        User interlocutor = (chat != null) ? chat.getInterlocutor(user) : null;
        Command command = new CommandParser(session,user,message, interlocutor).parse();
        command.execute();
    }

//    private Command parseCommand(Message message, User user) {
//        if (user == null) {
//            if (message.getCommandPrefix().equalsIgnoreCase(RegisterCommand.COM_PREFIX)) {
//                return RegisterCommand.parse(session, message.getCommandArgs());
//            } else {
//                return new WrongCommand(session, message.getContent(), "unregistered user: use /register <role> <name>");
//            }
//        } else {
//            if (message.isCommand()) {
//                switch (message.getCommandPrefix().toUpperCase()) {
//                    case LeaveCommand.COM_PREFIX:
//                        return LeaveCommand.parse(user);
//                    case ExitCommand.COM_PREFIX:
//                        return new ExitCommand(user);
//                    default:
//                        return new WrongCommand(session, message.getContent(), "wrong command");
//                }
//            } else return SendCommand.parse(user, message);
//        }
//    }

    public IncomingMessageHandler(Session session, Message message ) {
        this.message = message;
        this.session = session;
    }
}
