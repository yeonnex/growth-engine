package me.seoyeon.githubclient;

import java.util.List;

import me.seoyeon.githubclient.dto.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "github-client", url = "https://api.github.com")
public interface GithubClient {
  /**
   * GitHub REST API > Git database > Trees <br>
   * <br>
   * {@code repo} 파라미터로 전달된 Github 저장소의 최상위 항목 목록을 조회한다. 항목은 파일(blob) 또는 디렉토리(tree)이다.
   *
   * @param owner 저장소 소유자 (Github 사용자명)
   * @param repo 조회할 저장소명
   * @return 최상위 항목 목록
   */
  @GetMapping("/repos/{owner}/{repo}/git/trees/master")
  GitHubTopLevelContents fetchRepositoryRootContent(
      @PathVariable("owner") String owner, @PathVariable("repo") String repo);

  /**
   * GitHub REST API > Git database > Trees <br>
   * <br>
   * {@code treeSha} 파라미터로 전달된 트리(디렉토리)를 조회한다. 해당 API 를 재귀적으로 호출하여 디렉토리 내부 항목을 탐색할 수 있다.
   *
   * @param owner 저장소 소유지 (Github 사용자명)
   * @param repo 조회할 저장소명
   * @param treeSha 해당 트리 객체를 고유하게 식별하는 SHA1 해시값
   * @return 트래의 내용
   */
  @GetMapping("/repos/{owner}/{repo}/git/trees/{treeSha}")
  GitHubTreeContents fetchTreeContents(
      @PathVariable("owner") String owner,
      @PathVariable("repo") String repo,
      @PathVariable("treeSha") String treeSha);

  @GetMapping("/repos/{owner}/{repo}/git/blobs/{sha}")
  GitHubContentItem fetchContentItem(
      @PathVariable("owner") String owner,
      @PathVariable("repo") String repo,
      @PathVariable("sha") String sha);

  /**
   * 특정 기간 동안의 커밋 이력을 조회한다.
   *
   * @param owner 저장소 소유자 (예: "yeonnex")
   * @param repo 저장소 이름
   * @param since 조회 시작 시점 (ISO 8601 형식, 예: "2024-02-01T00:00:00Z")
   * @param until 조회 종료 시점 (ISO 8601 형식, 예: "2024-02-07T23:59:59Z")
   * @return 지정된 기간 내에 발생한 커밋 목록을 담은 {@link List<GithubCommit>}
   */
  @GetMapping("/repos/{owner}/{repo}/commits")
  List<GithubCommit> fetchNDaysAgoCommits(
      @PathVariable("owner") String owner,
      @PathVariable("repo") String repo,
      @RequestParam(name = "since") String since,
      @RequestParam(name = "until") String until);

  /**
   * 특정 커밋의 파일 목록을 조회한다.
   *
   * @param owner 저장소 소유자 (예: "yeonnex")
   * @param repo 저장소 이름
   * @param sha 조회할 커밋의 sha 값
   * @return 해당 커밋에 포함된 파일 목록을 담은 {@link GithubFiles} 객체
   */
  @GetMapping("/repos/{owner}/{repo}/commits/{sha}")
  GithubFiles fetchCommitFiles(
      @PathVariable("owner") String owner,
      @PathVariable("repo") String repo,
      @PathVariable("sha") String sha);
}
