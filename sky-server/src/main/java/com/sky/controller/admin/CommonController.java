package com.sky.controller.admin;

import com.aliyun.oss.internal.OSSUtils;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@ApiOperation(value = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation(value = "文件上传接口")
    public Result<String> upload(MultipartFile file)  {

        try {
            log.info("文件{}", file);
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString()+extension;
            String upload = aliOssUtil.upload(file.getBytes(), filename);
            return Result.success(upload);
        } catch (IOException e) {

           return Result.error("上传失败");
        }



    }
}
