package com.xelari.presencebot.application.adapter.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileStorageBoundary {

    String uploadFile(InputStream fileStream, String originalFilename, long contentSize) throws IOException;
    Boolean removeFile(String key) throws IOException;

}
