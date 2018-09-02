package com.aleksandrtikh.tschat.server;

import com.aleksandrtikh.tschat.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class ChatTest {


    @Before
    public void beforeEvery() {
        UserDataRepository.setUserBooker(new UserBookerImpl());
        UserDataRepository.setExistingUsers(new ConcurrentHashMap<>());
        UserDataRepository.setActiveChats(new ConcurrentHashMap<>());
    }

    @Test
    public void end() {
        final boolean[] agentFreed = {false};
        User agent = new User(null,null,null) {
            @Override
            public void free() {
                agentFreed[0] = true;
            }
        };
        User customer = new User(null,null,null);
        Chat chat = new Chat(agent,customer);
        UserDataRepository.getActiveChats().put(agent, chat);
        UserDataRepository.getActiveChats().put(customer, chat);
        chat.end();
        assertFalse(UserDataRepository.getActiveChats().containsValue(chat));
        assertTrue(agentFreed[0]);
    }

    @Test
    public void getInterlocutor() {
        User agent = new User(null,null,null);
        User customer = new User(null,null,null);
        Chat chat = new Chat(agent,customer);
        assertEquals(agent, chat.getInterlocutor(customer));
        assertEquals(customer, chat.getInterlocutor(agent));
        assertNull(chat.getInterlocutor(new User(null,null,null)));
    }

    @Test
    public void begin_1() {
        User agent = new User(null,null,null);
        User customer = new User(null,null,null);
        Chat chat = new Chat(agent,customer);
        chat.begin();
        assertEquals(chat,UserDataRepository.getActiveChats().get(agent));
        assertEquals(chat,UserDataRepository.getActiveChats().get(customer));
    }

    @Test
    public void begin_2() {
        final boolean[] messageSent = {false};
        User agent = new User(null,null,null) {
            @Override
            public void send(Message message) {
                messageSent[0] = true;
            }
        };
        User customer = new User(null,null,null) {
          @Override
          public boolean hasSavedMessages() {
              return true;
          }
          @Override
          public LinkedList<Message> getMessageBuffer() {
              return new LinkedList<Message>() {{ add(null);}};
          }
        };
        Chat chat = new Chat(agent,customer);
        chat.begin();
        assertTrue(messageSent[0]);
    }
}