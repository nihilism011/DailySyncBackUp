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
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(info());
    }
//    @Bean
//    public GroupedOpenApi groupedOpenApi() {
//        return GroupedOpenApi.builder()
//                .group("Account API")
//                .pathsToMatch("/account/**")
//                .addOpenApiCustomizer(accountApiCustomizer())
//                .build();
//    }
//
//    private OpenApiCustomizer accountApiCustomizer(){
//        return openApi -> openApi.info(new Info()
//                .title("Product API")
//                .description("API for managing products")
//                .version("1.0"));
//    }
    private Info info() {
        return new Info()
                .title("DailySync Team API")
                .description("API 명세서")
                .version("1.0");
    }
}
