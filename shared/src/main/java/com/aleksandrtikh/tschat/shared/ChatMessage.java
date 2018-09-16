package com.aleksandrtikh.tschat.shared;

public class ChatMessage {

    private String username;
    private String content;
    private MessageType type;

    public ChatMessage(String content, MessageType type, String username) {
        this.content = content;
        this.type = type;
        this.username = username;
    }

    public ChatMessage(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public String[] getCommandArgs() {
        return content.split(" ",4);
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public boolean isCommand() {
        return type == MessageType.REGISTER
                || type == MessageType.LEAVE
                || type == MessageType.EXIT;
    }

    public static ChatMessage parse(String content, String username) {
        ChatMessage message = new ChatMessage(content, username);
        MessageType type;
        if (content.startsWith("/")) {
            switch (message.getCommandArgs()[0].toUpperCase()) {
                case "/REGISTER" : type = MessageType.REGISTER; break;
                case "/LEAVE" : type = MessageType.LEAVE; break;
                case "/EXIT" : type = MessageType.EXIT; break;
                default: type = MessageType.ERROR;
            }
        } else {
            type = MessageType.SEND;
        }
        message.setType(type);
        return message;
    }

    public enum MessageType {
        // These 4 are for client use.
        SEND, REGISTER, LEAVE, EXIT,
        // And these 2 are for server.
        CONFIRM, ERROR
    }
}
