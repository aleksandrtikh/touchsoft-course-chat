package com.aleksandrtikh.tschat.server;



import com.alelsandrtikh.tschat.MessageTextDecoder;
import com.alelsandrtikh.tschat.MessageTextEncoder;
import org.glassfish.tyrus.server.Server;

import java.io.*;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.*;
import javax.websocket.*;
import javax.websocket.server.*;


public class WebSocketServerRunner {


    private static ConcurrentHashMap<User, Chat> activeChats;
    private static ConcurrentHashMap<Session, User> existingUsers;
    private static UserBooker userBooker;

    public static void main(String[] args) throws IOException {
        activeChats = new ConcurrentHashMap<>();
        existingUsers = new ConcurrentHashMap<>();
        userBooker = new UserBookerImpl();
        userBooker.addFreeEventListener(new ChatConnector());
        WebSocketContainer a = ContainerProvider.getWebSocketContainer();
        Server server = new Server("localhost", 8080, "", null, ChatServerEndpoint.class);
        try {
            server.start();
        } catch (DeploymentException e) {
            e.printStackTrace();
        }

        initializeServer();

        //ServerEndpointConfig.Builder.create(ChatServerEndpoint.class, "/coursechat");
        System.out.println("Server started");
        System.in.read();
    }

    private static void initializeServer() {
//        org.eclipse.jetty.server.WebSocketServerRunner server = new org.eclipse.jetty.server.WebSocketServerRunner();
//        ServerConnector connector = new ServerConnector(server);
//        connector.setPort(8080);
//        server.addConnector(connector);
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.setContextPath("/");
//        server.setHandler(context);
//        try
//        {
//            // Initialize javax.websocket layer
//            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);
//
//            // Add WebSocket endpoint to javax.websocket layer
//            wscontainer.addEndpoint(ChatServerEndpoint.class);
//
//            server.start();
//            server.dump(System.err);
//            server.join();
//        }
//        catch (Throwable t)
//        {
//            t.printStackTrace(System.err);
//        }
    }


    public static UserBooker getUserBooker() {
        return userBooker;
    }

    public static void setUserBooker(UserBooker userBooker) {
        WebSocketServerRunner.userBooker = userBooker;
    }

    public static ConcurrentHashMap<User, Chat> getActiveChats() {
        return activeChats;
    }

    public static void setActiveChats(ConcurrentHashMap<User, Chat> activeChats) {
        WebSocketServerRunner.activeChats = activeChats;
    }

    public static ConcurrentHashMap<Session, User> getExistingUsers() {
        return existingUsers;
    }

    public static void setExistingUsers(ConcurrentHashMap<Session, User> existingUsers) {
        WebSocketServerRunner.existingUsers = existingUsers;
    }
}
