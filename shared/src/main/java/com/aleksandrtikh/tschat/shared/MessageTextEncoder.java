package com.aleksandrtikh.tschat.shared;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import com.google.gson.*;

public class MessageTextEncoder implements Encoder.Text<ChatMessage> {

    private Gson encoder = new Gson();

    @Override
    public String encode(ChatMessage message) throws EncodeException {
        return encoder.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
