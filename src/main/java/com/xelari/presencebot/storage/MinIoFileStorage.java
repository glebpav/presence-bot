package com.xelari.presencebot.storage;

import com.xelari.presencebot.application.adapter.file.FileStorageBoundary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinIoFileStorage implements FileStorageBoundary {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String uploadFile(InputStream fileStream, String originalFilename, long contentSize) throws IOException {
        String key = UUID.randomUUID() + "-" + originalFilename;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(
                        fileStream,
                        contentSize
                )
        );

        return key;
    }

    @Override
    public Boolean removeFile(String key) throws IOException {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            s3Client.deleteObject(request);
            return true;
        } catch (S3Exception e) {
            throw new IOException("Error while deleting file in S3: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

}
