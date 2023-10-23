package com.casemodul4.config.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.List;

@Component
public class FileUtils {
    // Validate file
    public void validateFile( MultipartFile file) {
        // Kiểm tra tên file
        String fileName = file.getOriginalFilename();
        if(fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Invalid file name. The file name cannot be null or empty.");        }

        // image.png -> png
        // avatar.jpg -> jpg
        // Kiểm tra đuôi file (jpg, png, jpeg)
        String fileExtension = getFileExtension(fileName);
        if(!checkFileExtension(fileExtension)) {
            throw new IllegalArgumentException("Invalid file extension. Only specific file extensions are allowed.");        }

        // Kiểm tra dung lượng file (<= 10MB)
        double fileSize = (double) file.getSize() / (1024 * 1024); // Convert bytes to megabytes
        if (fileSize > 10) {
            throw new IllegalArgumentException("File size exceeds the maximum allowed limit of 10 MB.");
        }
        }


    // Lấy extension của file (ví dụ png, jpg, ...)
    public String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        return fileName.substring(lastIndexOf + 1);
    }

    // Kiểm tra extension của file có được phép hay không
    public boolean checkFileExtension(String fileExtension) {
        List<String> extensions = Arrays.asList("png", "jpg", "jpeg", "pdf");
        return extensions.contains(fileExtension.toLowerCase());
    }

}