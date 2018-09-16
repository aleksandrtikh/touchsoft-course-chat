package com.aleksandrtikh.tschat.server;

import com.aleksandrtikh.tschat.server.repository.UserBookerImpl;
import com.aleksandrtikh.tschat.shared.ChatMessage;
import com.aleksandrtikh.tschat.server.model.Chat;
import com.aleksandrtikh.tschat.server.model.User;
import com.aleksandrtikh.tschat.server.repository.UserDataRepository;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class UserTest {



    @Test
    public void send() {
        final boolean[] objectSent = {false};
        Session session = new SessionDummy() {
          @Override
          public RemoteEndpoint.Basic getBasicRemote() {
              return new BasicRemoteDummy() {
                  @Override
                  public void sendObject(Object o) {
                      objectSent[0] = true;
                  }
              };
          }
        };
        User user = new User(null, session, null );
        user.send(new ChatMessage(null,null,null));
        assertTrue(objectSent[0]);
    }
}