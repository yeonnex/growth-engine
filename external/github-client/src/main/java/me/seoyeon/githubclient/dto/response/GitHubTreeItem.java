package me.seoyeon.githubclient.dto.response;

import me.seoyeon.githubclient.type.GitHubTreeItemType;

public record GitHubTreeItem(
    String path, String mode, GitHubTreeItemType type, String sha, String url) {}
