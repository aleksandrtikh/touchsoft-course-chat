package com.aleksandrtikh.tschat;

import java.util.Date;

public class Message {
    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    private String[] commandArgs;
    private String content;
    private String date;
    private MessageType type;

    public Message(String content, MessageType type, String username) {
        this.content = content;
        this.date = new Date().toString();
        this.type = type;
        this.username = username;
    }

    public Message( String content, MessageType type,String username, String[] commandArgs) {
        this.username = username;
        this.commandArgs = commandArgs;
        this.content = content;
        this.type = type;
        this.date = new Date().toString();
    }
	
	 public MessageType getType() {
        return type;
    }

    public String getCommandPrefix() {
        return this.commandArgs[0];
    }
	
	public String[] getCommandArgs() {
        return commandArgs;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public boolean isCommand() {
        return type == MessageType.COMMAND;
    }

    public enum MessageType {
        COMMON_MESSAGE, COMMAND, CONFIRMATION, ERROR
    }

    public static Message parse(String content, String username) {
        Message.MessageType type;
        String[] commandArgs = null;
        if (content.startsWith("/")) {
            type = MessageType.COMMAND;
            int argsLimit = 4;
            commandArgs = content.split(" ", argsLimit);
        } else {
            type = MessageType.COMMON_MESSAGE;
        }
        return new Message(content,type,username, commandArgs);
    }
}
