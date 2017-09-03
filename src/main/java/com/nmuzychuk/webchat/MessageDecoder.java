package com.nmuzychuk.webchat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.time.LocalDateTime;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String s) throws DecodeException {
        Message message = new Message();
        JsonObject jsonObject =
                Json.createReader(new StringReader(s)).readObject();
        message.setBody(jsonObject.getString("body"));
        message.setSender(jsonObject.getString("sender"));
        message.setTime(LocalDateTime.now().toString());

        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
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
