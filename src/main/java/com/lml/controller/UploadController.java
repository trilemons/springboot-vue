package com.lml.controller;

import com.lml.pojo.Result;
import com.lml.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class UploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String string = UUID.randomUUID().toString();
        String filename = string+originalFilename.substring(originalFilename.lastIndexOf("."));
        //上传文件到本地
//        file.transferTo(new File("E:\\"+string+originalFilename.substring(originalFilename.lastIndexOf("."))));
        //上传到阿里云并返回访问地址
        String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success(url);
    }
}
