package com.sas.refactoring.chat.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자 자동 생성 X
@Entity(name="chat_log") // 테이블 명을 작성
public class ChatEntity {

    @Id @GeneratedValue
    private long uuid;

    @Column(name = "chat_time")
    private LocalDateTime chatTime;

    @Column(name = "user_name")
    private String userName;

    private String content;

    public ChatEntity(LocalDateTime chatTime, String userName, String content) {
        this.chatTime = chatTime;
        this.userName = userName;
        this.content = content;
    }
}
