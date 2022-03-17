package com.itheima.controller;

import com.itheima.controller.utils.ApiResult;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.sun.crypto.provider.HmacSHA1;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

@RestController
public class TestController {

    @GetMapping("/test")
    public ApiResult test() throws NoSuchAlgorithmException {
        Configuration cfg = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(cfg);

        String accessKey = "lBWb8CP90dSUR-ljomttFgKaJZYntGMCEKk8Oqt2";
        String secretKey = "Kbkff9JOeQr5ZtnGelmGiv2ARLjlkjA5_2_F34A8";
        String bucket = "test-20220313";
        String key = "key";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        FileInfo fileInfo = null;
        try {
            fileInfo = bucketManager.stat(bucket, key);
            System.out.println(fileInfo.hash);
            System.out.println(fileInfo.fsize);
            System.out.println(fileInfo.mimeType);
            System.out.println(fileInfo.putTime);
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
        return ApiResult.T("fileInfo",fileInfo);
    }
}
