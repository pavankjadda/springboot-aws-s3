package com.pj.service;

import com.pj.properties.AwsS3Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.DownloadFileRequest;
import software.amazon.awssdk.transfer.s3.progress.LoggingTransferListener;

import java.io.File;

/**
 * Core Service class that uploads and downloads from AWS S3
 *
 * @author Pavan Kumar Jadda
 * @since 2.3.7
 */
@Service
public class AwsS3ServiceImpl implements AwsS3Service {
    private final S3TransferManager transferManager;
    private final AwsS3Properties s3Properties;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AwsS3ServiceImpl(S3TransferManager transferManager, AwsS3Properties s3Properties) {
        this.transferManager = transferManager;
        this.s3Properties = s3Properties;
    }

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
    @Override
    public FileSystemResource downloadDocument(String fileName) {
        try {
            var tempFile = File.createTempFile("download-", "-" + fileName);
            var downloadFileRequest =
                    DownloadFileRequest.builder().getObjectRequest(req -> req.bucket(s3Properties.getBucket()).key(s3Properties.getKey())).destination(tempFile)
                            .addTransferListener(LoggingTransferListener.create()).build();

            // Download file and wait for the transfer to complete
            transferManager.downloadFile(downloadFileRequest).completionFuture().join();
            return new FileSystemResource(tempFile);
        } catch (Exception e) {
            logger.error("Unable to download the file from S3. Error:{}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return null;
    }
}
