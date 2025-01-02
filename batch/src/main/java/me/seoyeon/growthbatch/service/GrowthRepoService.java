package me.seoyeon.growthbatch.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import me.seoyeon.githubclient.GithubClient;
import me.seoyeon.githubclient.dto.response.GitHubContentItem;
import me.seoyeon.githubclient.dto.response.GitHubTopLevelContents;
import me.seoyeon.githubclient.dto.response.GitHubTreeContents;
import me.seoyeon.githubclient.dto.response.GitHubTreeItem;
import me.seoyeon.githubclient.type.GitHubTreeItemType;
import org.springframework.stereotype.Service;

@Service
public class GrowthRepoService {

  private final GithubClient githubClient;

  public GrowthRepoService(GithubClient githubClient) {
    this.githubClient = githubClient;
  }

  /**
   * growth-bytes 저장소에서 랜덤한 메모를 {@code count} 개 반환합니다.
   *
   * @param count 반환할 .md 파일의 개수
   * @return 랜덤하게 선택된 마크다운 메모
   */
  public List<GrowthMemoItem> pickRandomGrowthMemo(int count) {

    if (count > 5) {
      throw new IllegalArgumentException("요청된 개수는 5개 이하여야 합니다.");
    }

    GitHubTopLevelContents topLevelContents =
        githubClient.fetchTopLevelContents("yeonnex", "growth-bytes");

    // 최상위 항목들 중 BLOB 타입인 .gitignore 항목(GitHubTreeItemType.BLOB) 제거
    List<GitHubTreeItem> topLevelTree =
        topLevelContents.tree().stream()
            .filter(topLevelContent -> GitHubTreeItemType.TREE.equals(topLevelContent.type()))
            .collect(Collectors.toList());

    // 랜덤하게 섞기
    Collections.shuffle(topLevelTree);

    // count 개의 최상위 항목 추출
    if (topLevelTree.size() > count) {
      topLevelTree = topLevelTree.stream().limit(count).toList();
    }

    // 항목 타입이 GitHubTreeItemType.BLOB 이고 확장자가 .md 인 항목 추출
    List<GrowthMemoItem> memoItems = new ArrayList<>(count);

    for (GitHubTreeItem treeItem : topLevelTree) {
      GitHubTreeContents contents =
          githubClient.fetchTreeContents("yeonnex", "growth-bytes", treeItem.sha());
      GitHubTreeItem markdownMemoFile = getMarkdownMemoFile(contents);
      memoItems.add(GrowthMemoItem.create(markdownMemoFile.path(), markdownMemoFile.url()));
    }
    // 아직 마크다운 파일 개수를 채우지 못한 경우
    if (memoItems.size() < count) {
      // 더 조회해야 할 파일 개수
      int remainingCount = count - memoItems.size();
      for (int i = 0; i < remainingCount; i++) {
        Collections.shuffle(topLevelTree);
        GitHubTreeContents contents =
            githubClient.fetchTreeContents(
                "yeonnex", "growth-bytes", topLevelTree.getFirst().sha());
        GitHubTreeItem markdownMemoFile = getMarkdownMemoFile(contents);
        memoItems.add(GrowthMemoItem.create(markdownMemoFile.path(), markdownMemoFile.url()));
      }
    }
    return memoItems;
  }

  private GitHubTreeItem getMarkdownMemoFile(GitHubTreeContents contents) {
    return contents.tree().stream()
        .filter(item -> GitHubTreeItemType.BLOB.equals(item.type()) && item.path().endsWith(".md"))
        .findAny()
        .orElse(getMarkdownMemoFileRecursive(contents.tree(), 5));
  }

  private GitHubTreeItem getMarkdownMemoFileRecursive(List<GitHubTreeItem> treeItems, int depth) {

    if (depth <= 0 || treeItems.isEmpty()) {
      throw new IllegalArgumentException("Markdown 파일을 찾을 수 없습니다.");
    }

    Collections.shuffle(treeItems);

    GitHubTreeContents contents =
        githubClient.fetchTreeContents("yeonnex", "growth-bytes", treeItems.getFirst().sha());

    if (contents.tree().stream()
        .noneMatch(
            item -> GitHubTreeItemType.BLOB.equals(item.type()) && item.path().endsWith(".md"))) {
      return getMarkdownMemoFileRecursive(contents.tree(), depth - 1);
    }
    // 마크다운 파일 랜덤하게 섞기
    List<GitHubTreeItem> markDownFiles =
        contents.tree().stream()
            .filter(
                item -> GitHubTreeItemType.BLOB.equals(item.type()) && item.path().endsWith(".md"))
            .collect(Collectors.toList());
    Collections.shuffle(markDownFiles);
    return markDownFiles.getFirst();
  }

  public List<GitHubContentItem> getDetailMemos(List<GrowthMemoItem> memoItems) {
    List<GitHubContentItem> contentItems = new ArrayList<>();
    for (GrowthMemoItem memoItem : memoItems) {
      // "/"로 split하고 마지막 부분(hash 값)만 가져오기
      String[] parts = memoItem.url().split("/");
      String hash = parts[parts.length - 1]; // 파일 해시값
      GitHubContentItem contentItem =
          this.githubClient.fetchContentItem("yeonnex", "growth-bytes", hash);

      // Base64로 변환
      String content = new String(Base64.getMimeDecoder().decode(contentItem.content().getBytes()));
      GitHubContentItem item =
          new GitHubContentItem(
              memoItem.title(), contentItem.sha(), contentItem.size(), contentItem.url(), content);
      contentItems.add(item);
    }
    return contentItems;
  }
}
