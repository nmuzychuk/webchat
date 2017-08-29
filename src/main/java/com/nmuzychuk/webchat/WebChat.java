package com.nmuzychuk.webchat;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;

@ServerEndpoint(value = "/chat/{room}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class WebChat {

    private final Logger log = Logger.getLogger(getClass().getName());

    @OnOpen
    public void open(final Session session, @PathParam("room") final String room) {
        log.info(String.format("Session %s for room %s is opened", session.getId(), room));
        session.getUserProperties().put("room", room);
    }

    @OnMessage
    public void onMessage(final Session session, final Message message) throws IOException, EncodeException {
        log.info(String.format("Got message: %s", message));
        String room = (String) session.getUserProperties().get("room");

        for (Session s : session.getOpenSessions()) {
            if (s.isOpen() && room.equals(s.getUserProperties().get("room"))) {
                log.info(String.format("Sending object, session: %s", s.getId()));
                s.getBasicRemote().sendObject(message);
            }
        }
    }

}
