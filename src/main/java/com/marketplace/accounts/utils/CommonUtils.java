package com.marketplace.accounts.utils;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Component
public class CommonUtils {

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
