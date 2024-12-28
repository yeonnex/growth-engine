package me.seoyeon.githubclient;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("me.seoyeon.*")
public class GithubFeignClientConfig {}
