package com.aleksandrtikh.tschat.server;

import com.aleksandrtikh.tschat.Message;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class UserTest {

    @Before
    public void BeforeEvery() {
        UserDataRepository.setUserBooker(new UserBookerImpl());
        UserDataRepository.setExistingUsers(new ConcurrentHashMap<>());
        UserDataRepository.setActiveChats(new ConcurrentHashMap<>());
    }

    @Test
    public void hasInterlocutor_1() {
        User user1 = new User(null, null, null);
        User user2 = new User(null, null, null);
        Chat chat = new Chat(user1,user2);
        UserDataRepository.getActiveChats().put(user1, chat);
        UserDataRepository.getActiveChats().put(user2, chat);
        assertTrue(user1.hasInterlocutor());
        assertTrue(user2.hasInterlocutor());
    }

    @Test
    public void hasInterlocutor_2() {
        User user3 = new User(null, null, null);
        assertFalse(user3.hasInterlocutor());
    }

    @Test
    public void hasSavedMessages_1() {
        User agent = new User(null, null, User.Role.AGENT);
        assertFalse(agent.hasSavedMessages());
    }

    @Test
    public void hasSavedMessages_2() {
        User customer = new User(null, null, User.Role.CUSTOMER);
        assertFalse(customer.hasSavedMessages());
    }

    @Test
    public void hasSavedMessages_3() {
        User customer = new User(null, null, User.Role.CUSTOMER);
        customer.getMessageBuffer().offer(new Message(null,null,null));
        assertTrue(customer.hasSavedMessages());
    }

    @Test
    public void getChat_1() {
        User user1 = new User(null, null, null);
        User user2 = new User(null, null, null);
        Chat chat = new Chat(user1,user2);
        UserDataRepository.getActiveChats().put(user1, chat);
        UserDataRepository.getActiveChats().put(user2, chat);
        assertEquals(chat, user1.getChat());
        assertEquals(chat, user2.getChat());
    }

    @Test
    public void getChat_2() {
        User user = new User(null, null, null);
        assertNull(user.getChat());
    }

    @Test
    public void unfree_1() {
        User agent = new User(null, null, User.Role.AGENT);
        UserDataRepository.getUserBooker().freeAgent(agent);
        agent.unfree();
        assertFalse(UserDataRepository.getUserBooker().isUserFree(agent));
    }

    @Test
    public void unfree_2() {
        User customer = new User(null, null, User.Role.CUSTOMER);
        UserDataRepository.getUserBooker().freeCustomer(customer);
        customer.unfree();
        assertFalse(UserDataRepository.getUserBooker().isUserFree(customer));
    }

    @Test
    public void unregister() {
        Session session = new SessionDummy();
        User user = new User(null, session, null);
        UserDataRepository.getExistingUsers().put(session, user);
        user.unregister();
        assertFalse(UserDataRepository.getExistingUsers().containsKey(session));
        assertFalse(UserDataRepository.getExistingUsers().containsValue(user));
    }

    @Test
    public void getInterlocutor() {
        User user1 = new User(null, null, null);
        User user2 = new User(null, null, null);
        Chat chat = new Chat(user1,user2);
        UserDataRepository.getActiveChats().put(user1, chat);
        UserDataRepository.getActiveChats().put(user2, chat);
        assertEquals(user2, user1.getInterlocutor());
        assertEquals(user1, user2.getInterlocutor());
    }

    @Test
    public void register() {
        Session session = new SessionDummy();
        User user = new User(null, session, null);
        user.register();
        assertEquals(user,UserDataRepository.getExistingUsers().get(session));
    }

    @Test
    public void free_1() {
        User agent = new User(null, null, User.Role.AGENT);
        agent.free();
        assertTrue(UserDataRepository.getUserBooker().isUserFree(agent));
    }

    @Test
    public void free_2() {
        User customer = new User(null, null, User.Role.CUSTOMER);
        customer.free();
        assertTrue(UserDataRepository.getUserBooker().isUserFree(customer));
    }

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
        user.send(new Message(null,null,null));
        assertTrue(objectSent[0]);
    }
}