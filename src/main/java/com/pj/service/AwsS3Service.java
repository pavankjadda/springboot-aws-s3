package com.pj.service;

import org.springframework.core.io.FileSystemResource;

import java.io.IOException;

/**
 * Core Service class that uploads and downloads from AWS S3
 *
 * @author Pavan Kumar Jadda
 * @since 2.3.7
 */
public interface AwsS3Service {
    /**
     * Download the file from S3
     *
     * @param fileName S3 Location of the file
     *
     * @return Resource Object of the file
     *
     * @author Pavan Kumar Jadda
     * @since 2.3.7
     */
    FileSystemResource downloadDocument(String fileName) throws InterruptedException, IOException;
}
