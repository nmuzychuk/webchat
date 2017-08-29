package com.nmuzychuk.webchat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String s) throws DecodeException {
        Message message = new Message();
        JsonObject jsonObject =
                Json.createReader(new StringReader(s)).readObject();
        message.setBody(jsonObject.getString("body"));
        message.setSender(jsonObject.getString("sender"));

        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

}
