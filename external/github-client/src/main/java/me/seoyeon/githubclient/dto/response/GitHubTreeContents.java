package me.seoyeon.githubclient.dto.response;


import java.util.List;

public record GitHubTreeContents(String sha, String url, List<GitHubTreeItem> tree) {}
