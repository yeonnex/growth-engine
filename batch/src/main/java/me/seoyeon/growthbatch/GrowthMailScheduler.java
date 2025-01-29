package me.seoyeon.growthbatch;

import java.util.List;
import me.seoyeon.githubclient.dto.response.GitHubContentItem;
import me.seoyeon.growthbatch.service.GrowthMemoItem;
import me.seoyeon.growthbatch.service.GrowthRepoService;
import me.seoyeon.mailclient.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GrowthMailScheduler {

  private final Logger log = LoggerFactory.getLogger(GrowthMailScheduler.class);

  private final EmailSender eMailSender;
  private final GrowthRepoService growthRepoService;

  public GrowthMailScheduler(EmailSender emailSender, GrowthRepoService growthRepoService) {
    this.eMailSender = emailSender;
    this.growthRepoService = growthRepoService;
  }

  @Scheduled(cron = "0 15 8,21 * * *")
  public void sendMailToMe() {
    log.info("발송 준비...");
    List<GrowthMemoItem> memoItems = growthRepoService.pickRandomGrowthMemo(3);
    List<GitHubContentItem> detailMemos = growthRepoService.getDetailMemos(memoItems);
    detailMemos.forEach(
        memo -> {
          eMailSender.sendHTMLEmail("syhoneyjam@naver.com", memo.title(), memo.content());
        });
  }
}
