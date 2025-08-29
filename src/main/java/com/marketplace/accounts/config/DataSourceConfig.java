package com.marketplace.accounts.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.accounts.utils.CommonUtils;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

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
        String secret = getSecret(secretName, region);

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
    /**
     * Fetches a secret value from AWS Secrets Manager.
     *
     * @param secretName the name of the secret to retrieve
     * @param region     AWS region where the secret is stored
     * @return the secret string retrieved from Secrets Manager
     */
    public static String getSecret(String secretName, String region) {

        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.of(region))
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        try {
            GetSecretValueResponse getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
            return getSecretValueResponse.secretString();
        } catch (Exception e) {
            // For a list of exceptions thrown, see
            // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
            throw new RuntimeException("Unable to retrieve secret from AWS Secrets Manager", e);
        }
    }
}
