package com.pj.web;

import com.pj.service.AwsS3Service;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/crms/protocol/document")
public class AwsController {
    private final AwsS3Service awsS3Service;

    public AwsController(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    /**
     * Download Document by Document ID
     *
     * @param fileName Document ID of the File that needs to be downloaded
     *
     * @return Selected version of the Document
     *
     * @author Pavan Kumar Jadda
     * @since 2.3.7
     */
    @GetMapping("/download/{fileName}")
    public FileSystemResource downloadDocument(@PathVariable String fileName) throws IOException, InterruptedException {
        return awsS3Service.downloadDocument(fileName);
    }
}
