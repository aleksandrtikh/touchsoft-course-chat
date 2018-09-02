package com.aleksandrtikh.tschat.server;



import org.glassfish.tyrus.server.Server;
import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


public class WebSocketServerRunner {



    public final static String SERVER_NAME = "Server";

    public static void main(String[] args) throws IOException {
        UserDataRepository.setActiveChats(new ConcurrentHashMap<>());
        UserDataRepository.setExistingUsers(new ConcurrentHashMap<>());
        UserBooker booker = new UserBookerImpl();
        booker.addFreeEventListener(new ChatConnector());
        UserDataRepository.setUserBooker(booker);
        Server server = new Server("localhost", 8080, "", null, ChatServerEndpoint.class);
        try {
            server.start();
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
        System.out.println("Server started");
        System.in.read();
    }


}
