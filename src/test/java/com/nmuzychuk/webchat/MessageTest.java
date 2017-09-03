package com.nmuzychuk.webchat;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MessageTest {

    private Message message;

    @Before
    public void init() {
        message = new Message();
    }

    @Test
    public void testMessage() {
        message.setBody("b");
        message.setSender("s");
        message.setTime(LocalDateTime.now().toString());

        assertEquals("{body: b, sender: s}", message.toString());
        assertNotNull(message.getTime());
    }

}
