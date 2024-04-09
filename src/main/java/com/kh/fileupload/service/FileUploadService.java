package com.kh.fileupload.service;

import com.kh.fileupload.entity.FileUpload;
import com.kh.fileupload.model.Base64FileUploadRequest;

public interface FileUploadService {

    FileUpload save(Base64FileUploadRequest request);

    FileUpload findById(Long id);

    void deleteById(Long id);
}
