package com.svalero.springweb.api_config;

import io.swagger.models.HttpMethod;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                in = SecuritySchemeIn.HEADER,
                bearerFormat = "JWT",
                scheme = "bearer")
public class ShopConfig {

    @Bean
    public OpenAPI myOpenAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Aitor's API shop")
                                .description("A_A 2ª Evaluación Acceso a datos")
                                .contact(new Contact()
                                        .name("Aitor Poquet")
                                        .email("a24837@svalero.com"))
                                .version("1.0"));
    }
}
