package com.aleksandrtikh.tschat.client;


import com.aleksandrtikh.tschat.shared.ChatMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import org.apache.log4j.*;


public class Client {

    private static final Logger log = Logger.getLogger(Client.class);
    static boolean isWorking = true;
    static boolean isRegistered = false;
    private static final URI serverURI = URI.create("ws://localhost:8080/chat");
    static String username = "user";
    private static Session session;
    private static ChatEndpoint endpoint;

    public static void main(String[] args) {
        try {
            establishConnection();
            work();
        } catch (DeploymentException | IOException e) {
            System.out.println("An error has occurred, check log for details. \nThe application will be closed now.");
            log.error(e);
            System.exit(1);
        }
    }

    private static void establishConnection() throws IOException, DeploymentException {
        endpoint = new ChatEndpoint();
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(endpoint, serverURI);
    }

    private static void work() throws IOException {
        Scanner reader = new Scanner(System.in);
        while (isWorking) {
            String line = reader.nextLine();
            ChatMessage message = ChatMessage.parse(line, username);
            if (line.toUpperCase().startsWith("/EXIT")) {
                exit(message);
            } else if (isRegistered == line.toUpperCase().startsWith("/REGISTER")) { // If registered, can't register again.
                System.out.println("Forbidden action.");                             // If not, should register first.
            } else endpoint.sendMessage(message);
        }
    }

    private static void exit(ChatMessage message) throws IOException {
        if (isRegistered) {
            endpoint.sendMessage(message);
        }
        final String NORMAL_CLOSE_REASON = "Client closed";
        session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, NORMAL_CLOSE_REASON));
        System.exit(0);
    }


}



