package com.alelsandrtikh.tschat;

import java.time.Instant;
import java.util.Date;

public class Message {
    private String commandPrefix;
    private String[] commandArgs;
    private String content;
    private Date date;

    public MessageType getType() {
        return type;
    }

    private MessageType type;

    public Message(String content, MessageType type) {
        this.content = content;
        this.date = Date.from(Instant.now());
        this.type = type;
    }

    public String getCommandPrefix() {
        return this.commandPrefix;
    }

    public boolean isCommand() {
        return type == MessageType.COMMAND;
    }

    public String[] getCommandArgs() {
        return commandArgs;
    }

    public String getContent() {
        return content;
    }


    public enum MessageType {
        COMMON_MESSAGE, COMMAND, CONFIRMATION, ERROR
    }

    public Message(String content) {
        this.content = content;
        if (content.startsWith("/")) {
            type = MessageType.COMMAND;
            int argsLimit = 4;
            commandArgs = content.split(" ", argsLimit);
            commandPrefix = commandArgs[0];
        } else {
            type = MessageType.COMMON_MESSAGE;
        }
        date = Date.from(Instant.now());
    }
}
