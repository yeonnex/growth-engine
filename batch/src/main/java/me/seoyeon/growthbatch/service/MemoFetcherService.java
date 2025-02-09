package me.seoyeon.growthbatch.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import me.seoyeon.githubclient.GithubClient;
import me.seoyeon.githubclient.dto.response.GithubCommit;
import me.seoyeon.githubclient.dto.response.GithubFile;
import me.seoyeon.githubclient.dto.response.GithubFiles;
import me.seoyeon.mailclient.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoFetcherService {

  private static final Logger log = LoggerFactory.getLogger(MemoFetcherService.class);
  private final List<MemoFetcher> memoFetchers;
  private final GithubClient githubClient;
  private final EmailSender emailSender;

  @Autowired
  public MemoFetcherService(
      List<MemoFetcher> memoFetchers, GithubClient githubClient, EmailSender emailSender) {
    this.memoFetchers = memoFetchers;
    this.githubClient = githubClient;
    this.emailSender = emailSender;
  }

  // 네트워크에서 마크다운 파일 읽기
  private static String fetchMarkdownFromUrl(String fileUrl) {
    StringBuilder content = new StringBuilder();

    try {
      URL url = new URL(fileUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      try (BufferedReader reader =
          new BufferedReader(
              new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
        String line;
        while ((line = reader.readLine()) != null) {
          content.append(line).append("\n");
        }
      }

    } catch (Exception e) {
      log.error("파일을 가져오는 중 오류 발생: {}", e.getMessage());
      throw new RuntimeException(e);
    }

    return content.toString();
  }

  public int fetchAndSendMemos() {
    int totalSentMemos = 0;
    for (MemoFetcher memoFetcher : this.memoFetchers) {
      List<GithubCommit> items = memoFetcher.fetch();
      int memoCnt = processFetchedMemos(items);
      log.info("{} {} memo sent.", memoCnt, memoFetcher.getName());
      totalSentMemos += memoCnt;
    }
    return totalSentMemos;
  }

  private int processFetchedMemos(List<GithubCommit> items) {
    int memoCnt = 0;
    for (GithubCommit item : items) {
      GithubFiles files = githubClient.fetchCommitFiles("yeonnex", "growth-bytes", item.sha());
      List<GithubFile> githubFiles = files.files();
      for (GithubFile githubFile : githubFiles) {
        // .md 로 끝나는 파일 추출
        if (githubFile.filename().endsWith(".md")) {
          String markdownContent = fetchMarkdownFromUrl(githubFile.rawUrl());
          // 파일명만 추출(디렉토리 경로명 제거)
          String filename = githubFile.filename();
          String[] parts = filename.split("/");
          String onlyFilename = parts[parts.length - 1];

          emailSender.sendHTMLEmail("syhoneyjam@naver.com", onlyFilename, markdownContent);
          memoCnt += 1;
        }
      }
    }
    return memoCnt;
  }
}
