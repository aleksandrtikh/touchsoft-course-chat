package com.aleksandrtikh.tschat.client;



import com.alelsandrtikh.tschat.Message;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;


public class Client {

    public static boolean isWorking = true;
    public static boolean isRegistered = false;
    private static final URI serverURI = URI.create("ws://localhost:8080/coursechat");

    public static void main(String[] args) {
        ChatEndpoint endpoint = new ChatEndpoint();
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        Session session = null;
        try {
           session =  container.connectToServer(endpoint, serverURI);
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner reader = new Scanner(System.in);
        while (isWorking) {
            String line = reader.nextLine();
            Message message = new Message(line);
            String exitPrefix = "/EXIT";
            if (line.toUpperCase().startsWith(exitPrefix)) {
                if (isRegistered) {
                    endpoint.sendMessage(message);
                }

                try {
                    session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Client closed"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            String registerPrefix = "/REGISTER";
            if (isRegistered == line.toUpperCase().startsWith(registerPrefix)) {
                System.out.println("Forbidden action.");
                continue;
            }
            endpoint.sendMessage(message);
        }
    }


}



