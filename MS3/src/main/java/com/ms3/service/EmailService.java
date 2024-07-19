package com.ms3.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String to, String token) {
        String subject = "비밀번호 재설정 요청";
        String resetUrl = "http://localhost:3000/password-reset?token=" + token;
        String message = "<p>비밀번호 재설정을 요청하셨습니다.</p>"
                       + "<p>아래 링크를 클릭하여 비밀번호를 재설정하세요:</p>"
                       + "<p><a href=\"" + resetUrl + "\">비밀번호 재설정 링크</a></p>"
                       + "<br>"
                       + "<p>이 링크는 1시간 동안 유효합니다.</p>";

        sendEmail(to, subject, message);
    }

    private void sendEmail(String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // true는 HTML을 사용하겠다는 의미
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }
}
