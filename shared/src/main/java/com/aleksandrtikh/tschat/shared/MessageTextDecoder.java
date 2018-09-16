package com.aleksandrtikh.tschat.shared;

import com.google.gson.Gson;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageTextDecoder implements Decoder.Text<ChatMessage>{

    @Override
    public ChatMessage decode(String s) throws DecodeException {
        return new Gson().fromJson(s, ChatMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
