package com.kh.fileupload.service;

import com.kh.fileupload.entity.FileUpload;
import com.kh.fileupload.model.Base64FileUploadRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    FileUpload save(Base64FileUploadRequest request);

    FileUpload saveMultiPartFile(MultipartFile file) throws IOException;

    FileUpload findById(Long id);

    void deleteById(Long id);
}
