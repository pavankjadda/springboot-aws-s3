package com.pj.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pj.properties.AwsS3Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
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
    private final S3Client s3Client;
    private final EmployeeService employeeService;

    private final AwsS3Properties s3Properties;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AwsS3ServiceImpl(S3TransferManager transferManager, S3Client s3Client, EmployeeService employeeService, AwsS3Properties s3Properties) {
        this.transferManager = transferManager;
        this.s3Client = s3Client;
        this.employeeService = employeeService;
        this.s3Properties = s3Properties;
    }

    /**
     * Download the file from S3
     *
     * @return Resource Object of the file
     *
     * @author Pavan Kumar Jadda
     * @since 2.3.7
     */
    @Override
    public FileSystemResource downloadDocumentUsingTransferManager() {
        try {
            var tempFile = File.createTempFile("download-", "-createAndUploadFile.pdf");
            var downloadFileRequest = DownloadFileRequest.builder().getObjectRequest(req -> req.bucket(s3Properties.getBucket()).key(s3Properties.getKey()))
                    .destination(tempFile).addTransferListener(LoggingTransferListener.create()).build();

            // Download file and wait for the transfer to complete
            transferManager.downloadFile(downloadFileRequest).completionFuture().join();
            return new FileSystemResource(tempFile);
        } catch (Exception e) {
            logger.error("Unable to download the file from S3. Error:{}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return null;
    }

    @Override
    public FileSystemResource downloadDocumentUsingS3Client() {
        try {
            var tempFile = File.createTempFile("download-", "-createAndUploadFile.pdf");
            var getObjectResponse = s3Client.getObject(req -> req.bucket(s3Properties.getBucket()).key(s3Properties.getKey()), tempFile.toPath());
            getObjectResponse.wait();
            return new FileSystemResource(tempFile);
        } catch (Exception e) {
            logger.error("Unable to download the file from S3. Error:{}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return null;
    }

    @Override
    public void listObjects() {
        s3Client.listObjectsV2(req -> req.bucket(s3Properties.getBucket())).contents().forEach(obj -> logger.info("Object Key:{}", obj.key()));
    }

    @Override
    public void createAndUploadFile() {
        var tempFile = getJsonFile();
        try {
            if (tempFile != null) {
                // Creates a directory in the bucket if it does not exist
                var putOb = PutObjectRequest.builder()
                        .bucket(s3Properties.getBucket())
                        .key("protect/" + tempFile.getName())
                        .build();
                var response = s3Client.putObject(putOb, RequestBody.fromFile(tempFile));
                logger.info("File:{} uploaded successfully. ETag:{}", tempFile.getName(), response.eTag());
            }

        } catch (Exception e) {
            logger.error("Unable to upload the file to S3. Error:{}", e.getMessage());
        } finally {
            if (tempFile != null) {
                var deleted = tempFile.delete();
                logger.info("Temp file deleted:{}", deleted);
            }
        }
    }

    private File getJsonFile() {
        File jsonFile = null;
        var employees = employeeService.getEmployees();

        try {
            jsonFile = new File("employees.json");
            new ObjectMapper().writeValue(new File("employees.json"), employees);
        } catch (Exception e) {
            System.err.println("Error writing JSON: " + e.getMessage());
        }
        return jsonFile;
    }
}
