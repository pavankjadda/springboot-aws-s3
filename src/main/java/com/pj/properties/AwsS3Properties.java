package com.pj.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Maps AWS S3 properties to AwsS3Properties bean
 *
 * @author Pavan Kumar Jadda
 * @since 2.3.7
 */
@Configuration
@ConfigurationProperties(prefix = "aws.s3")
public class AwsS3Properties {
    private String bucket;
    private String key;

    public AwsS3Properties() {
    }

    public AwsS3Properties(String bucket, String key) {
        this.bucket = bucket;
        this.key = key;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
