# Spring Boot and AWS S3 demo

This repository that demonstrates how to upload and download files from AWS S3 using Spring Boot and Cloud AWS S3

## How to run

1. Clone this repository and open it in your favorite IDE
2. Run the `SpringBootAwsS3Application` class
3. Make sure to replace the `accessKey` and `secretKey` in the `application.yml` file with your own credentials
4. Open your browser and go to GET http://localhost:8081/api/v1/document/download

## How load AWS credentials

1. Define `CustomAWSCredentialsProvider` class and implement `AWSCredentialsProvider` interface
2. Or define it inline in [AwsConfig](com/pj/config/AwsConfig.java) class and use `BasicAWSCredentials` class and
   pass `accessKey` and `secretKey` to it
