package me.seoyeon.githubclient.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 특정 커밋에서 추가/변경된 파일 DTO
 * @param filename 파일명
 * @param rawUrl 파일 URL
 */
public record GithubFile(String filename, @JsonProperty("raw_url") String rawUrl) {}
