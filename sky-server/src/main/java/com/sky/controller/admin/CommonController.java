package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
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
@Api(tags = "通用接口")
@Slf4j

public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 通用接口用于文件上传
     *
     * @param file
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);

        String name = null;
        try {
            String originalFileName = file.getOriginalFilename();
            assert originalFileName != null;
            name = UUID.randomUUID() + originalFileName.substring(originalFileName.indexOf("."));
            return Result.success(aliOssUtil.upload(file.getBytes(), name));
        } catch (IOException e) {
            log.error("文件上传失败：{}", e.getMessage());
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
