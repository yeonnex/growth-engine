package me.seoyeon.mailclient;

import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

  private final Logger log = LoggerFactory.getLogger(EmailSender.class.getName());

  private final JavaMailSender javaMailSender;

  public EmailSender(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  public void sendEmail(String to, String subject, String text) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper =
          new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

      helper.setFrom("syhoneyjam@naver.com");
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(text, true);

      javaMailSender.send(message);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }
}
