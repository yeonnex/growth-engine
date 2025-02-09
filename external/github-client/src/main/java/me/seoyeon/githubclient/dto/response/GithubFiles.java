package me.seoyeon.githubclient.dto.response;

import java.util.List;
import me.seoyeon.githubclient.dto.response.GithubFile;

/**
 * 특정 커밋에서 추가/변경된 파일 목록 DTO
 * @param files
 */
public record GithubFiles(List<GithubFile> files) {}
