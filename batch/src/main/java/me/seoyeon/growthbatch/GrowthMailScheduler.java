package me.seoyeon.growthbatch;

import java.util.List;
import me.seoyeon.githubclient.dto.response.GitHubContentItem;
import me.seoyeon.growthbatch.dto.GrowthMemoItem;
import me.seoyeon.growthbatch.service.MemoFetcherService;
import me.seoyeon.growthbatch.service.RandomMemoFetcherService;
import me.seoyeon.mailclient.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GrowthMailScheduler {

  private final Logger log = LoggerFactory.getLogger(GrowthMailScheduler.class);

  private final EmailSender eMailSender;
  private final RandomMemoFetcherService randomMemoFetcherService;
  private final MemoFetcherService memoFetcherService;

  public GrowthMailScheduler(
      EmailSender emailSender,
      RandomMemoFetcherService randomMemoFetcherService,
      MemoFetcherService memoFetcherService) {
    this.eMailSender = emailSender;
    this.randomMemoFetcherService = randomMemoFetcherService;
    this.memoFetcherService = memoFetcherService;
  }

  @Scheduled(fixedDelay = 300000)
  //  @Scheduled(fixedDelay = 1000)
  //    @Scheduled(cron = "0 0 8,21 * * *")
  public void sendMailToMe() {
    // 1,3,7일 전 커밋의 메모 발송
//    int totalSentMemos = this.memoFetcherService.fetchAndSendMemos();
//    if (totalSentMemos >= 5) return;
    // 랜덤 메모 3개 발송 TODO 랜덤 메모 발송 자체도 MemoFetcher 인터페이스 구현하는 방식으로 설계하기
    List<GrowthMemoItem> memoItems = this.randomMemoFetcherService.pickRandomGrowthMemo(3);
    List<GitHubContentItem> detailMemos = this.randomMemoFetcherService.getDetailMemos(memoItems);
    detailMemos.forEach(
        memo -> {
          eMailSender.sendHTMLEmail("syhoneyjam@naver.com", memo.title(), memo.content());
        });
  }
}
