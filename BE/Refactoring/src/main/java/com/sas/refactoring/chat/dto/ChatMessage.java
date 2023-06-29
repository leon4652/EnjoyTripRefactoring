package com.sas.refactoring.chat.dto;

import java.time.LocalDateTime;

public class ChatMessage {
    private String userName;
    private String content;
    private LocalDateTime chatTime;

    public ChatMessage(String userName, String content, LocalDateTime chatTime) {
        this.userName = userName;
        this.content = content;
        this.chatTime = chatTime;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getChatTime() {
        return chatTime;
    }

    public void setChatTime(LocalDateTime chatTime) {
        this.chatTime = chatTime;
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
