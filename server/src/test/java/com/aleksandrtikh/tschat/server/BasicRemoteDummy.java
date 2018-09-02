package com.aleksandrtikh.tschat.server;

import javax.websocket.RemoteEndpoint;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;

class BasicRemoteDummy implements RemoteEndpoint.Basic {
    @Override
    public void sendText(String s) {

    }

    @Override
    public void sendBinary(ByteBuffer byteBuffer) {

    }

    @Override
    public void sendText(String s, boolean b) {

    }

    @Override
    public void sendBinary(ByteBuffer byteBuffer, boolean b) {

    }

    @Override
    public OutputStream getSendStream() {
        return null;
    }

    @Override
    public Writer getSendWriter() {
        return null;
    }

    @Override
    public void sendObject(Object o) {

    }

    @Override
    public void setBatchingAllowed(boolean b) {

    }

    @Override
    public boolean getBatchingAllowed() {
        return false;
    }

    @Override
    public void flushBatch() {

    }

    @Override
    public void sendPing(ByteBuffer byteBuffer) throws IllegalArgumentException {

    }

    @Override
    public void sendPong(ByteBuffer byteBuffer) throws IllegalArgumentException {

    }
}
