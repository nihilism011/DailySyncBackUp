package com.dailySync.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    //http://localhost:8080/swagger-ui/index.html
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("Account API")
                .pathsToMatch("/api/account/**")
                .addOpenApiCustomizer(accountApiCustomizer())
                .build();
    }
    @Bean
    public GroupedOpenApi autoGenApi() {
        return GroupedOpenApi.builder()
                .group("Auto Jpa Account API")
                .pathsToMatch("/accounts/**")
                .addOpenApiCustomizer(accountApiCustomizer())
                .build();
    }
    @Bean
    public GroupedOpenApi groupedOpenApi2() {
        return GroupedOpenApi.builder()
                .group("Meal API")
                .pathsToMatch("/api/meal/**")
                .addOpenApiCustomizer(accountApiCustomizer())
                .build();
    }
    @Bean
    public GroupedOpenApi autoGenApi2() {
        return GroupedOpenApi.builder()
                .group("Auto Jpa Meal API")
                .pathsToMatch("/meals/**")
                .addOpenApiCustomizer(accountApiCustomizer())
                .build();
    }
    private OpenApiCustomizer accountApiCustomizer(){
        return openApi -> openApi.info(new Info()
                .title("Product API")
                .description("API for managing products")
                .version("1.0"));
    }
}