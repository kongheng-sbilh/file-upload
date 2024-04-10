package com.kh.fileupload.service;

import com.kh.fileupload.entity.FileUpload;
import com.kh.fileupload.model.Base64FileUploadRequest;
import com.kh.fileupload.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Override
    public FileUpload save(Base64FileUploadRequest request) {
        String filePath = getAppDataFolderPath() + File.separator + request.getFileName() + "." + request.getFileExtension();
        byte[] decodedBytes = Base64.getDecoder().decode(request.getImageBase64());
        try (OutputStream stream = new FileOutputStream(filePath)) {
            stream.write(decodedBytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileUpload fileUpload = FileUpload.builder()
            .fileName(request.getFileName())
            .filePath(filePath)
            .build();
        return fileUploadRepository.save(fileUpload);
    }

    private String getAppDataFolderPath() {
        String homeDirectory = System.getProperty("user.home");
        String appDataFolderPath = homeDirectory + File.separator + "Documents" + File.separator + "AppData";
        File appDataFolder = new File(appDataFolderPath);
        if (!appDataFolder.exists()) {
            appDataFolder.mkdirs();
        }
        return appDataFolderPath;
    }

    @Override
    public FileUpload saveMultiPartFile(MultipartFile file) {
        Path filePath = Paths.get(getAppDataFolderPath() + File.separator + file.getOriginalFilename());
        saveFileToLocalMachine(file, filePath);
        return saveFileToDatabase(file, filePath);
    }

    private FileUpload saveFileToDatabase(MultipartFile file, Path path) {
        FileUpload fileUpload = FileUpload.builder()
            .fileName(file.getOriginalFilename())
            .filePath(path.toString())
            .build();
        return fileUploadRepository.save(fileUpload);
    }

    private void saveFileToLocalMachine(MultipartFile file, Path path) {
        try {
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
