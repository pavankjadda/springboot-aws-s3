package com.pj.config;

import io.awspring.cloud.autoconfigure.core.CredentialsProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

public class CustomAWSCredentialsProvider implements AwsCredentialsProvider {
    private final CredentialsProperties properties;

    public CustomAWSCredentialsProvider(CredentialsProperties properties) {
        this.properties = properties;
    }

    /**
     * Returns {@link AwsCredentials} that can be used to authorize an AWS request. Each implementation of AWSCredentialsProvider
     * can choose its own strategy for loading credentials. For example, an implementation might load credentials from an existing
     * key management system, or load new credentials when credentials are rotated.
     *
     * <p>If an error occurs during the loading of credentials or credentials could not be found, a runtime exception will be
     * raised.</p>
     *
     * @return AwsCredentials which the caller can use to authorize an AWS request.
     */
    @Override
    public AwsCredentials resolveCredentials() {
        return AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey());
    }
}
