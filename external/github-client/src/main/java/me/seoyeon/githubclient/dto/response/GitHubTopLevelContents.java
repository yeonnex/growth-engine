package me.seoyeon.githubclient.dto.response;

import java.util.List;

/** 깃헙 저장소 최상위 항목 응답 DTO */
public record GitHubTopLevelContents(String sha, String url, List<GitHubTreeItem> tree) {}
