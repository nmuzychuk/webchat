package com.nmuzychuk.webchat;

public class Message {

    private String body;
    private String sender;
    private String time;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("{body: %s, sender: %s}", this.getBody(), this.getSender());
    }

}
