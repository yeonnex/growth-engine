package me.seoyeon.growthbatch.service.impl;

import me.seoyeon.githubclient.GithubClient;
import org.springframework.stereotype.Component;

/**
 * 14일 전에 작성된 메모(.md) 파일을 Github 리포지토리에서 가져오는 구현체
 * <p>{@link BaseDaysAgoMemoFetcher}를 상속받아 7일 전의 메모를 가져오는 기능을 구현</p>
 */
@Component
public class TwoWeeksAgoMemoFetcher extends BaseDaysAgoMemoFetcher {
  public TwoWeeksAgoMemoFetcher(GithubClient githubClient) {
    super(githubClient, 14);
  }

  @Override
  public String getName() {
    return "TwoWeeks";
  }
}
