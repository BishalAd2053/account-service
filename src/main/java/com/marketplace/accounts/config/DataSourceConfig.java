package com.marketplace.accounts.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.accounts.utils.CommonUtils;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Configures the application's {@link DataSource} by retrieving database
 * credentials from AWS Secrets Manager. The secret is expected to contain a
 * JSON document with keys: username, password, host, port and dbname.
 */
@Configuration
@Profile("uat")
public class DataSourceConfig {

    @Value("${aws.secret.name:db_connect}")
    private String secretName;

    @Value("${aws.region:us-east-1}")
    private String region;

    @Bean
    public DataSource dataSource() throws Exception {
        String secret = CommonUtils.getSecret(secretName, region);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(secret);

        String username = root.get("username").asText();
        String password = root.get("password").asText();
        String host = root.get("host").asText();
        String dbname = root.get("dbname").asText();
        int port = root.get("port").asInt();

        String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, dbname);

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }
}
