package com.aleksandrtikh.tschat.server;

import com.aleksandrtikh.tschat.server.controller.ChatConnector;
import com.aleksandrtikh.tschat.server.controller.ChatServerEndpoint;
import com.aleksandrtikh.tschat.server.repository.ChatRepository;
import com.aleksandrtikh.tschat.server.repository.UserBooker;
import com.aleksandrtikh.tschat.server.repository.UserBookerImpl;
import com.aleksandrtikh.tschat.server.repository.UserDataRepository;
import com.aleksandrtikh.tschat.server.service.ChatService;
import org.apache.log4j.Logger;
import org.glassfish.tyrus.server.Server;
import javax.websocket.DeploymentException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketServerRunner {

	private static Logger log = Logger.getLogger(WebSocketServerRunner.class);
    public final static String SERVER_NAME = "Server";

    public static void main(String[] args) {
        UserDataRepository userDataRepository = UserDataRepository.getInstance();
        ChatRepository chatRepository = ChatRepository.getInstance();
        chatRepository.setAll(new ConcurrentHashMap<>());
        userDataRepository.setExistingUsers(new ConcurrentHashMap<>());
        UserBooker booker = new UserBookerImpl();
        booker.addFreeEventListener(new ChatConnector(new ChatService()));
        userDataRepository.setUserBooker(booker);
        Server server = new Server("localhost", 8080, "", null, ChatServerEndpoint.class);
        try {
            server.start();
        } catch (DeploymentException e) {
            log.error(e);
        }
        System.out.println("Server started, type \"/exit\" to stop.");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.nextLine().equalsIgnoreCase("/exit")) {
                System.exit(0);
            }
        }
    }

}
