# Spring cloud AWS S3 bug

This repository is a sample project to reproduce a bug in Spring Cloud AWS S3 that fails to read credentials from
the application.yml file.

## How to reproduce

1. Clone this repository and open it in your favorite IDE
2. Run the `SpringBootAwsS3BugApplication` class
3. Make sure to replace the `accessKey` and `secretKey` in the `application.yml` file with your own credentials
   4Open your browser and go to GET http://localhost:8081/api/v1/document/download

## Solution

1. Define `CustomAWSCredentialsProvider` class and implement `AWSCredentialsProvider` interface
2. Or define it inline in [AwsConfig](com/pj/config/AwsConfig.java) class and use `BasicAWSCredentials` class and
   pass `accessKey` and `secretKey` to it
