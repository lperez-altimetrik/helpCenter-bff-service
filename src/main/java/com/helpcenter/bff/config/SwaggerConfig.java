package com.helpcenter.bff.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HelpCenter BFF API")
                        .version("1.0")
                        .description("Backend For Frontend (BFF) API for HelpCenter application")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@helpcenter.com")
                        )
                );
    }
}
