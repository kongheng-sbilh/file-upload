package com.kh.fileupload.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Base64FileUploadRequest {
    private String imageBase64;
    private String fileName;
    private String fileExtension;
}
