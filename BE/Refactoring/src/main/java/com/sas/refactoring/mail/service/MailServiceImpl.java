package com.sas.refactoring.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender emailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendCheckMail(List<String> data) throws Exception {
        System.out.println(data.get(1) + " : " + data.get(2));
        SimpleMailMessage message = new SimpleMailMessage(); //STMP 메시지 객체
        message.setFrom("csg1353@naver.com");
        message.setTo(data.get(2));
        message.setSubject("안녕하세요, " + data.get(1) + "님. 회원가입 확인 서비스입니다.");
        message.setText("회원가입 안내 : ");
        emailSender.send(message);
    }
}
