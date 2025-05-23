package com.example.career.global.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendEMail(String to, String title, String content) {

        System.out.println("try send mail : " + to);

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText(content);

            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            System.err.println("[이메일 전송 오류] " + e.getMessage());
            e.printStackTrace();
        }
    }
}
