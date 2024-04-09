package com.kh.fileupload.controller;

import com.kh.fileupload.model.Base64FileUploadRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Base64;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @PostMapping
    public String uploadFile(@RequestBody Base64FileUploadRequest request) {
        String homeDirectory = System.getProperty("user.home");
        String appDataFolderPath = homeDirectory + File.separator + "Documents" + File.separator + "AppData";
        File appDataFolder = new File(appDataFolderPath);
        if (!appDataFolder.exists()) {
            appDataFolder.mkdirs();
        }
        String filePath = appDataFolderPath + File.separator + request.getFileName() + "." + request.getFileExtension();
        byte[] decodedBytes = Base64.getDecoder().decode(request.getImageBase64());
        try (OutputStream stream = new FileOutputStream(filePath)) {
            stream.write(decodedBytes);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Files uploaded successfully";
    }
}
