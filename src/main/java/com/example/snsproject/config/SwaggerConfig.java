package com.example.snsproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "SNS 프로젝트 API 명세서",
        description = "실험에 사용될 SNS API 명세서",
        version = "v1"
    )
)
@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
        .addServersItem(new Server().url("/"));
  }
}
