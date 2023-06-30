package com.sas.refactoring.chat.controller;


import com.sas.refactoring.chat.dto.ChatMessage;
import com.sas.refactoring.chat.entity.ChatEntity;
import com.sas.refactoring.chat.entity.ChatRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    private final ChatRepository chatRepository;

    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @MessageMapping("/receive") // /receive를 메시지를 받을 endpoint로 설정합니다. (메시지 매핑)
    @SendTo("/send") // /send로 메시지를 반환합니다. (config)
    public ChatMessage SocketHandler(ChatMessage chatMessage) {
        String userName = chatMessage.getUserName();
        String content = chatMessage.getContent();
        LocalDateTime chatTime = chatMessage.getChatTime();


        //MySQL DB 저장
        chatRepository.save(new ChatEntity(chatTime, userName, content));
        System.out.println("저장 완료");
        // 메시지 타입 FE로 반환
        return new ChatMessage(userName, content, chatTime);
    }

}
