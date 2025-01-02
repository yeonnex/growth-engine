package me.seoyeon.githubclient.dto.response;

public record GitHubContentItem(
    String title, String sha, Integer size, String url, String content) {}
