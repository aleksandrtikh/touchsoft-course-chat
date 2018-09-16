package com.aleksandrtikh.tschat.server.command;


import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.server.repository.ChatRepository;
import com.aleksandrtikh.tschat.server.service.ChatService;

public class LeaveCommand implements Command {

    private final User user;
    ChatService service = new ChatService();

    public void execute() {
        ChatRepository repository = ChatRepository.getInstance();
        service.endChat(repository.getChatByUser(user));
    }


    public LeaveCommand(User user) {
        this.user = user;
    }
}
