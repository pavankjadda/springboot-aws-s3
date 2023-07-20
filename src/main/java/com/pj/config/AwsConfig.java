package com.pj.config;

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
public class AwsConfig {
    /**
     * Build AWS S3 Async Client
     *
     * @author Pavan Kumar Jadda
     * @since 2.3.28
     */
    @Bean
    public S3AsyncClient s3AsyncClient() {
        return S3AsyncClient.crtBuilder()
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
        return S3TransferManager.create();
    }
}

