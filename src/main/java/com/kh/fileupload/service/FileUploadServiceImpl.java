package com.kh.fileupload.service;

import com.kh.fileupload.entity.FileUpload;
import com.kh.fileupload.model.Base64FileUploadRequest;
import com.kh.fileupload.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Override
    public FileUpload save(Base64FileUploadRequest request) {
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
        FileUpload fileUpload = FileUpload.builder()
            .fileName(request.getFileName())
            .filePath(filePath)
            .build();
        return fileUploadRepository.save(fileUpload);
    }

    @Override
    public FileUpload findById(Long id) {
        return fileUploadRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        fileUploadRepository.deleteById(id);
    }
}
