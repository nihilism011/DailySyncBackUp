package com.dailySync.config;

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
                .group("Auto Account API")
                .pathsToMatch("/accounts/**", "/favoriteAccounts/**")
                .addOpenApiCustomizer(accountApiCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi todoApi() {
        return GroupedOpenApi.builder()
                .group("Todo API")
                .pathsToMatch("/api/todo/**")
                .addOpenApiCustomizer(accountApiCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi autoT() {
        return GroupedOpenApi.builder()
                .group("Auto Todo API")
                .pathsToMatch("/todoItems/**", "/todoLists/**")
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
                .group("Auto Meals API")
                .pathsToMatch("/meals/**")
                .addOpenApiCustomizer(accountApiCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi alls() {
        return GroupedOpenApi.builder()
                .group("ALL API")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(accountApiCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi sch() {
        return GroupedOpenApi.builder()
                .group("Schedule API")
                .pathsToMatch("/api/schedule/**")
                .addOpenApiCustomizer(accountApiCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi autoC() {
        return GroupedOpenApi.builder()
                .group("Auto Schedule API")
                .pathsToMatch("/schedules/**")
                .addOpenApiCustomizer(accountApiCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi userE() {
        return GroupedOpenApi.builder()
                .group("Auto User API")
                .pathsToMatch("/users/**", "/userSettings/**")
                .addOpenApiCustomizer(accountApiCustomizer())
                .build();
    }

    private OpenApiCustomizer accountApiCustomizer() {
        return openApi -> openApi.info(new Info()
                .title("Product API")
                .description("API for managing products")
                .version("1.0"));
    }
}