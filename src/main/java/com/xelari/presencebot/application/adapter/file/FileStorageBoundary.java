package com.xelari.presencebot.application.adapter.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageBoundary {

    String uploadFile(MultipartFile file) throws IOException;
    Boolean removeFile(String key) throws IOException;

}
