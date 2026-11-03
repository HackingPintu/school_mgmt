package com.ssvm.school.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI schoolOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SSVM School Management System API")
                        .description("REST API documentation for student enrollment, records, and photo management")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("School Administration")
                                .email("admin@ssvm.school")));
    }
}