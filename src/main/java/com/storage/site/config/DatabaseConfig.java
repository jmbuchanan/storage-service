package com.storage.site.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Configuration
@Profile("local-k8s")
@AllArgsConstructor
@Slf4j
public class DatabaseConfig {

    private JdbcTemplate jdbcTemplate;
    private ResourceLoader resourceLoader;


    @PostConstruct
    public void insertDataIfEmpty() {
        //create the schema
        String schemaSql = toSqlString("_schema");
        if (schemaSql != null) {
            jdbcTemplate.execute(schemaSql);
        }
        //insert data if tables are empty
        List<String> sqlScripts = List.of("customers", "prices", "units");
        sqlScripts.stream()
                .filter(this::isTableEmpty)
                .map(this::toSqlString)
                .filter(Objects::nonNull)
                .forEach(sql -> jdbcTemplate.update(sql));
    }

    private String toSqlString(String resourcePath) {
        Resource resource = resourceLoader.getResource("classpath:sql/" + resourcePath + ".sql");
        try {
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Could not read file {} in resources", resourcePath);
            return null;
        }
    }

    private boolean isTableEmpty(String tableName) {
        log.info("Checking if table {} is empty.", tableName);
        String sql = String.format("SELECT COUNT(*) FROM %s", tableName);
        Integer rows = jdbcTemplate.queryForObject(sql, Integer.class);
        log.info("Found {} rows in {}.", rows, tableName);
        return rows != null && rows.equals(0);
    }
}
