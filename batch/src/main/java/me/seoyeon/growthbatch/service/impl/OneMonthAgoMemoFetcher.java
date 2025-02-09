package me.seoyeon.growthbatch.service.impl;

import me.seoyeon.githubclient.GithubClient;
import org.springframework.stereotype.Component;

/**
 * 한달 전에 작성된 메모(.md) 파일을 Github 리포지토리에서 가져오는 구현체
 *
 * <p>{@link BaseDaysAgoMemoFetcher}를 상속받아 한달 전의 메모를 가져오는 기능을 구현
 */
@Component
public class OneMonthAgoMemoFetcher extends BaseDaysAgoMemoFetcher {
  public OneMonthAgoMemoFetcher(GithubClient githubClient) {
    super(githubClient, 30);
  }

  @Override
  public String getName() {
    return "OneMonth";
  }
}
