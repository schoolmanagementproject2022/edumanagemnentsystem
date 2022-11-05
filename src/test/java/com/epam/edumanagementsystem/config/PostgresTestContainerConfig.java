package com.epam.edumanagementsystem.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class PostgresTestContainerConfig {

    private static final String DB_SCHEMA = "test";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "root";

    @Container
    public static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:latest")
                    .withDatabaseName(DB_SCHEMA)
                    .withUsername(DB_USERNAME)
                    .withPassword(DB_PASSWORD);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

}
