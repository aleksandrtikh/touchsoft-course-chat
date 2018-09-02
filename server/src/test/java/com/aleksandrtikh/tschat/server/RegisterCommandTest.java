package com.aleksandrtikh.tschat.server;

import com.aleksandrtikh.tschat.Message;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class RegisterCommandTest {


    @Test
    public void execute_1() {
        final boolean[] calledMethods = {false, false, false};
        String username = "test";
        User user = new User(username, new SessionDummy(), User.Role.AGENT) {
            @Override
            public void send(Message message) {
                if ((Objects.equals(message.getContent(), "successfully registered"))
                        && (message.getType() == Message.MessageType.CONFIRMATION)
                        && (Objects.equals(message.getUsername(), username))) {
                    calledMethods[0]= true;
                }
            }

            @Override
            public void free() {
                calledMethods[1] = true;
            }

            @Override
            public void register() {
                calledMethods[2] = true;
            }
        };
        new RegisterCommand(user).execute();
        assertTrue(calledMethods[0]);
        assertTrue(calledMethods[1]);
        assertTrue(calledMethods[2]);
    }
    @Test
    public void execute_2() {
        final boolean[] calledMethods = {false, true, false};
        String username = "test";
        User user = new User(username, new SessionDummy(), User.Role.CUSTOMER) {
            @Override
            public void send(Message message) {
                if ((Objects.equals(message.getContent(), "successfully registered"))
                        && (message.getType() == Message.MessageType.CONFIRMATION)
                        && (Objects.equals(message.getUsername(), username))) {
                    calledMethods[0]= true;
                }
            }

            @Override
            public void free() {
                calledMethods[1] = false;
            }

            @Override
            public void register() {
                calledMethods[2] = true;
            }
        };
        new RegisterCommand(user).execute();
        assertTrue(calledMethods[0]);
        assertTrue(calledMethods[1]);
        assertTrue(calledMethods[2]);
    }
}