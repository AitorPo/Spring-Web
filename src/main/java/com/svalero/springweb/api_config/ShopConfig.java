package com.svalero.springweb.api_config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

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
