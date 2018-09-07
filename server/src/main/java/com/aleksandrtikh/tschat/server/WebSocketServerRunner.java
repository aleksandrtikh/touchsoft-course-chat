package com.aleksandrtikh.tschat.server;

import com.aleksandrtikh.tschat.server.controller.ChatConnector;
import com.aleksandrtikh.tschat.server.controller.ChatServerEndpoint;
import com.aleksandrtikh.tschat.server.repository.UserDataRepository;
import org.apache.log4j.Logger;
import org.glassfish.tyrus.server.Server;
import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketServerRunner {

	private static Logger log = Logger.getLogger(WebSocketServerRunner.class);
    public final static String SERVER_NAME = "Server";
    private static float aFloat;

    public static void main(String[] args) {
        UserDataRepository.setActiveChats(new ConcurrentHashMap<>());
        UserDataRepository.setExistingUsers(new ConcurrentHashMap<>());
        UserBooker booker = new UserBookerImpl();
        booker.addFreeEventListener(new ChatConnector());
        UserDataRepository.setUserBooker(booker);
        Server server = new Server("localhost", 8080, "", null, ChatServerEndpoint.class);
        try {
            server.start();
        } catch (DeploymentException e) {
            log.error(e);
        }
        System.out.println("Server started");
        try {
            System.in.read();
        } catch (IOException e) {}
    }

}
