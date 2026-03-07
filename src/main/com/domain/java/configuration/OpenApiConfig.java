package com.domain.java.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI loanPaymentOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Loan Payment System API")
                        .description("API documentation for the Loan Payment System")
                        .version("v1.0.0"));
    }
}