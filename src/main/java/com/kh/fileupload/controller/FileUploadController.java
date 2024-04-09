package com.kh.fileupload.controller;

import com.kh.fileupload.entity.FileUpload;
import com.kh.fileupload.model.Base64FileUploadRequest;
import com.kh.fileupload.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping
    public ResponseEntity<FileUpload> uploadFile(@RequestBody Base64FileUploadRequest request) {
        FileUpload fileUpload = fileUploadService.save(request);
        return ResponseEntity.ok(fileUpload);
    }

    @PostMapping("/v2")
    public ResponseEntity<String> handleFileUpload(@RequestPart("file")MultipartFile file) throws IOException {
        fileUploadService.saveMultiPartFile(file);
        return ResponseEntity.ok("Files uploaded successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileUpload> getFileById(@PathVariable Long id) {
        return ResponseEntity.ok(fileUploadService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        fileUploadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
