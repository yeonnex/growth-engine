package me.seoyeon.growthbatch.service;

import java.util.List;
import me.seoyeon.githubclient.dto.response.GitHubTreeItem;
import me.seoyeon.githubclient.dto.response.GithubCommit;

/**
 * Github 에서 특정 조건에 맞는 메모(.md 파일)를 가져오는 인터페이스입니다.
 * 구현 클래스에서 각 기준(어제 작성한 메모, 랜덤 메모 등)에 맞는 로직을 정의합니다.
 */
public interface MemoFetcher {
  /**
   * 특정 기준에 따라 Github 리포지토리에서 메모 파일을 가져옵니다.
   *
   * @return 메모 파일 정보
   */
  List<GithubCommit> fetch();

  String getName();
}
