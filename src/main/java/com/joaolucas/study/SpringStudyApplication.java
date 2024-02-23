package com.joaolucas.study;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "E-commerce API", version = "1.0.0"))
@SecurityScheme(type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer", name = "bearerAuth")
@SpringBootApplication
public class SpringStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStudyApplication.class, args);
    }

}
