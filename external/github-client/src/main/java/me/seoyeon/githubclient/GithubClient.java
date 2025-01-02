package me.seoyeon.githubclient;

import me.seoyeon.githubclient.dto.response.GitHubContentItem;
import me.seoyeon.githubclient.dto.response.GitHubTopLevelContents;
import me.seoyeon.githubclient.dto.response.GitHubTreeContents;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
  GitHubTopLevelContents fetchTopLevelContents(
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
}
