package com.sas.refactoring.chat.dto;

public class ChatMessage {
    private String userName;
    private String content;

    public String getUserName() {
        return userName;
    }

    public ChatMessage(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
