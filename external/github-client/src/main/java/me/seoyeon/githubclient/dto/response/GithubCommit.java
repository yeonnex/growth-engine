package me.seoyeon.githubclient.dto.response;

/**
 * 특정 커밋 응답 DTO
 * @param sha 특정 커밋의 sha 값
 * @param url 특정 커밋의 URL
 * @param commit 특정 커밋 정보(커밋메시지)
 * @param author 특정 커밋의 작성자
 */
public record GithubCommit(String sha, String url, CommitInfo commit, Author author) {}

record CommitInfo(String message) {}

record Author(String login) {}
