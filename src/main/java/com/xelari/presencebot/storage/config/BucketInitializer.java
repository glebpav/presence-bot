package com.xelari.presencebot.storage.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Component
@RequiredArgsConstructor
public class BucketInitializer {

    private final S3Client s3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @PostConstruct
    public void init() {
        try {
            if (!s3Client.listBuckets().buckets().stream().anyMatch(b -> b.name().equals(bucketName))) {
                s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
            }
        } catch (S3Exception e) {
            throw new RuntimeException("Error while initialization MinIO: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

}