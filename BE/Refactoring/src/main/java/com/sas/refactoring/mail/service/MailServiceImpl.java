package com.sas.refactoring.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.activation.URLDataSource;
import java.net.URL;
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
        SimpleMailMessage message = new SimpleMailMessage(); //STMP 메시지 객체
        message.setFrom("syhfqq1810@gmail.com");
        message.setTo(data.get(3));
        message.setSubject("회원 가입 인증 안내");

        message.setText("안녕하세요 " + data.get(2) +"("+data.get(0)+")"+"님. " +
                "\n 가입 안내 메일입니다." +
                "\n 회원가입 처리를 위해 다음 코드를 로그인 시 입력해 주세요." +
                "\n 인증 코드 : " + data.get(4));
        emailSender.send(message);
    }
}
