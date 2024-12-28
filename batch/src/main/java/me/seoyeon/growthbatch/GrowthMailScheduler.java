package me.seoyeon.growthbatch;

import me.seoyeon.mailclient.EmailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GrowthMailScheduler {

  private static int count;
  private final EmailSender eMailSender;

  public GrowthMailScheduler(EmailSender emailSender) {
    this.eMailSender = emailSender;
  }

  @Scheduled(fixedDelay = 7000)
  public void sendMailToMe() {
    System.out.println("이메일 전송 준비중...");
    eMailSender.sendEmail("syhoneyjam@naver.com", "서연이 안녕 " + count, "안뇽 " + count);
    count++;
    System.out.println("이메일 전송 완료!");
  }
}
