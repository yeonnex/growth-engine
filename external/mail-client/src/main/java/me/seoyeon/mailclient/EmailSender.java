package me.seoyeon.mailclient;

import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import me.seoyeon.mailclient.util.MarkdownToHtmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

  private final Logger log = LoggerFactory.getLogger(EmailSender.class.getName());

  private final JavaMailSender javaMailSender;
  private final MarkdownToHtmlConverter markdownToHtmlConverter;

  public EmailSender(
      JavaMailSender javaMailSender, MarkdownToHtmlConverter markdownToHtmlConverter) {
    this.javaMailSender = javaMailSender;
    this.markdownToHtmlConverter = markdownToHtmlConverter;
  }

  public void sendSimpleTextEmail(String to, String subject, String text) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper =
          new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

      helper.setFrom("syhoneyjam@naver.com");
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(text, true);
      message.setContent(text, "text/html; charset=UTF-8");

      javaMailSender.send(message);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  public void sendHTMLEmail(String to, String subject, String text) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper =
          new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

      helper.setFrom("syhoneyjam@naver.com");
      helper.setTo(to);
      helper.setSubject(subject);
      message.setContent(this.markdownToHtmlConverter.convert(text), "text/html; charset=UTF-8");
      javaMailSender.send(message);

      log.info("[{}] 메일 발송 완료.", subject);

    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }
}
