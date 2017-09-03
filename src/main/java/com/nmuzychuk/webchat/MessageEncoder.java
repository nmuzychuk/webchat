package com.nmuzychuk.webchat;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(Message message) throws EncodeException {
        return Json.createObjectBuilder()
                .add("body", message.getBody())
                .add("sender", message.getSender())
                .add("time", message.getTime())
                .build().toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // No implementation necessary
    }

    @Override
    public void destroy() {
        // No implementation necessary
    }

}
