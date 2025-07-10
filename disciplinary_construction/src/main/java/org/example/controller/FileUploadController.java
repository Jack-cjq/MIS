package org.example.controller;

import org.example.response.ResponseResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/msi/upload")
@CrossOrigin
public class FileUploadController {

    // 上传目录，相对于项目根目录
    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping
    public ResponseResult uploadFiles(@RequestParam("file") MultipartFile[] files) {
        List<String> fileUrls = new ArrayList<>();

        try {
            // 创建上传目录
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // 生成唯一文件名
                    String originalFilename = file.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String filename = UUID.randomUUID().toString() + extension;

                    // 保存文件
                    Path filePath = uploadPath.resolve(filename);
                    Files.copy(file.getInputStream(), filePath);

                    // 添加文件URL
                    fileUrls.add("/uploads/" + filename);
                }
            }

            return ResponseResult.success(fileUrls);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.error("文件上传失败: " + e.getMessage());
        }
    }

    @PostMapping("/single")
    public ResponseResult uploadSingleFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseResult.error("文件不能为空");
            }

            // 创建上传目录
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;

            // 保存文件
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            // 返回文件URL
            String fileUrl = "/uploads/" + filename;
            return ResponseResult.success(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.error("文件上传失败: " + e.getMessage());
        }
    }
}