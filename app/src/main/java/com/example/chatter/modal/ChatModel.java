package com.example.chatter.modal;

public class ChatModel {
    String userId,message;
    Long timestamp;

    public ChatModel(String userId, String message, Long timestamp){
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ChatModel(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public ChatModel(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
