package com.pj.config;

import io.awspring.cloud.autoconfigure.core.CredentialsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

/**
 * Configuration class that defines AWS clients i.e.S3 Client
 *
 * @author Pavan Kumar Jadda
 * @since 2.3.7
 */
@Configuration
@EnableConfigurationProperties(CredentialsProperties.class)
public class AwsConfig {
    private final CredentialsProperties properties;

    public AwsConfig(CredentialsProperties properties) {
        this.properties = properties;
    }


    /**
     * Build AWS S3 Async Client
     *
     * @author Pavan Kumar Jadda
     * @since 2.3.28
     */
    @Bean
    public S3AsyncClient s3AsyncClient() {
        return S3AsyncClient.crtBuilder()
                .credentialsProvider(new CustomAWSCredentialsProvider(properties))
                //.credentialsProvider(() -> AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey()))
                .region(Region.US_EAST_1)
                .build();
    }

    /**
     * Build AWS S3 TransferManager bean
     *
     * @author Pavan Kumar Jadda
     * @since 2.3.28
     */
    @Bean
    public S3TransferManager s3TransferManager() {
        return S3TransferManager.builder().s3Client(s3AsyncClient()).build();
    }
}

