package com.xiami.web;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xiami.base.ResponseResult;
import com.xiami.dto.FileInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Author：郑锦
 * Date：2020­05­26 22:47
 * Description：<描述>
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    private static final String ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAI4Fbts3BgyVqvN8HUjWUd";
    private static final String ACCESS_KEY_SECRET = "rHSv3EQtgc38tmlqEsi2k0gLXwrG32";
    private static final String BUCKET_NAME = "youyasumi-oss";

    @PostMapping("")
    public ResponseResult upload(MultipartFile multipartFile){
        String fileName=multipartFile.getOriginalFilename();
        String suffix= fileName.substring(fileName.lastIndexOf(".")+1);
        String newName = UUID.randomUUID() + "." + suffix;
        OSS client = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            client.putObject(BUCKET_NAME, newName, new ByteArrayInputStream(multipartFile.getBytes()));
            return new ResponseResult<FileInfo>(ResponseResult.CodeStatus.FAIL, "文件上传成功", new FileInfo("http://" + BUCKET_NAME + "." + ENDPOINT + "/" + newName));
        } catch (IOException e) {
            return new ResponseResult<FileInfo>(ResponseResult.CodeStatus.FAIL, "文件上传失败，请重试");
        }finally {
            client.shutdown();
        }
    }
}
