package com.example.fcmtoken;


public class ModelChat {
    String message;
    String type;
    String timestamp;
    boolean dilihat;

    String id;

    public ModelChat(String message, String receiver, String sender, String type, String timestamp, boolean dilihat, String id ) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
        this.type = type;
        this.timestamp = timestamp;
        this.dilihat = dilihat;
        this.id=id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDilihat() {
        return dilihat;
    }

    public void setDilihat(boolean dilihat) {
        this.dilihat = dilihat;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String receiver;

    public ModelChat() {
    }

    String sender;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
