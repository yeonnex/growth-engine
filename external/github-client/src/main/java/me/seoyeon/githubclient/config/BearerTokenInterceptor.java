package me.seoyeon.githubclient.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** GitHub API 호출을 위해 액세스 토큰을 설정하는 인터셉터이다. 모든 API 호출 요청시 요청 헤더에 해당 토큰이 탑재된다. */
@Component
public class BearerTokenInterceptor implements RequestInterceptor {

  @Value("${api.bearer-token}")
  private String bearerToken;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate.header("Authorization", "Bearer " + bearerToken);
  }
}
