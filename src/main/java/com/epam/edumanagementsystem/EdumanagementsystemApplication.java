package com.epam.edumanagementsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "School management app", version = "1.0.0", description = "The best app ever!"))
public class EdumanagementsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdumanagementsystemApplication.class, args);
    }
}
