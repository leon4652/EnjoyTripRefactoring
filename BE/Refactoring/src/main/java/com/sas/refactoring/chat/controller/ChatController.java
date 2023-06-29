package com.sas.refactoring.chat.controller;


import com.sas.refactoring.chat.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    // /receive를 메시지를 받을 endpoint로 설정합니다. (메시지 매핑)
    @MessageMapping("/receive")
    // /send로 메시지를 반환합니다. (config)
    @SendTo("/send")

    public ChatMessage SocketHandler(ChatMessage chatMessage) {
        String userName = chatMessage.getUserName();
        String content = chatMessage.getContent();
        LocalDateTime chatTime = chatMessage.getChatTime();


        // 메시지 타입 반환
        return new ChatMessage(userName, content, chatTime);
    }

}
