package me.seoyeon.growthbatch.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import me.seoyeon.githubclient.GithubClient;
import me.seoyeon.githubclient.dto.response.GithubCommit;
import me.seoyeon.growthbatch.service.MemoFetcher;

/** 특정 n 일 전의 메모를 가져오는 기본 MemoFetcher. */
public abstract class BaseDaysAgoMemoFetcher implements MemoFetcher {
  protected final GithubClient githubClient;
  private final Integer daysAgo;

  public BaseDaysAgoMemoFetcher(GithubClient githubClient, Integer daysAgo) {
    this.githubClient = githubClient;
    this.daysAgo = daysAgo;
  }

  @Override
  public List<GithubCommit> fetch() {
    // Github API는 UTC(세계 협정시) 기준으로 동작함.
    // 따라서, 한국시간(KST, UTC+9)에서 원하는 날짜의 커밋을 조회하려면 UTC로 변환해야 함.

    // 한국 시간대를 기준으로 설정
    ZoneId koreaZone = ZoneId.of("Asia/Seoul");
    // 조회할 날짜 계산
    LocalDate yesterdayKST = LocalDate.now(koreaZone).minusDays(this.daysAgo);

    Instant sinceUtc = yesterdayKST.atStartOfDay(koreaZone).toInstant();
    Instant untilUtc = yesterdayKST.plusDays(1).atStartOfDay(koreaZone).minusSeconds(1).toInstant();

    String since = sinceUtc.toString(); // 예: "2025-02-07T15:00:00Z" (한국 시간 2월 8일 00:00:00)
    String until = untilUtc.toString(); // 예: "2025-02-08T14:59:59Z" (한국 시간 2월 8일 23:59:59)

    return githubClient.fetchNDaysAgoCommits("yeonnex", "growth-bytes", since, until);
  }
}
