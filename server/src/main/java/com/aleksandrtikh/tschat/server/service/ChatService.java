package com.aleksandrtikh.tschat.server.service;

import com.aleksandrtikh.tschat.server.model.Chat;
import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.server.repository.ChatRepository;
import com.aleksandrtikh.tschat.server.repository.UserDataRepository;
import com.aleksandrtikh.tschat.shared.ChatMessage;
import org.apache.log4j.Logger;

import java.util.LinkedList;

public class ChatService {

    private static final Logger log = Logger.getLogger(ChatService.class);
    private UserDataRepository userDataRepository = UserDataRepository.getInstance();
    private ChatRepository chatRepository = ChatRepository.getInstance();
    private UserService service = new UserService();

    public void beginChat(Chat chat) {
        User agent = chat.getAgent();
        User customer = chat.getCustomer();
        chatRepository.getAll().put(agent, chat);
        chatRepository.getAll().put(customer, chat);
        if (customer.hasSavedMessages()) {
            LinkedList<ChatMessage> buffer = customer.getMessageBuffer();
            while (!buffer.isEmpty()) {
                agent.send(buffer.poll());
            }
        }
        log.info(String.format("Chat started between agent %s and customer %s.", agent.getUserName(), customer.getUserName()));
    }

    public void endChat(Chat chat) {
        User agent = chat.getAgent();
        User customer = chat.getCustomer();
        chatRepository.getAll().remove(agent, chat);
        chatRepository.getAll().remove(customer, chat);
        service.freeUser(agent);
        log.info(String.format("Chat ended between agent %s and customer %s.", agent.getUserName(), customer.getUserName()));
    }

}
