package com.itheima.controller;


import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.FileStorage;
import com.itheima.service.FileStorageService;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/getToken")
    public ApiResult getToken(){
        String accessKey = "lBWb8CP90dSUR-ljomttFgKaJZYntGMCEKk8Oqt2";
        String secretKey = "Kbkff9JOeQr5ZtnGelmGiv2ARLjlkjA5_2_F34A8";
        String bucket = "test-20220313";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return ApiResult.T(upToken);
    }

    @GetMapping("{id}")
    public ApiResult getFileById(@PathVariable Integer id){
        return ApiResult.T(fileStorageService.getFileById(id));
    }

    @PostMapping("/addNewFile")
    public ApiResult addNewFile(@RequestBody FileStorage fileStorage) {
        try {
            fileStorageService.saveFile(fileStorage);
        } catch (Exception e) {
//            if (e.){
//                return ApiResult.F("","fdfdfdfdfd");
//            }
            return ApiResult.F();
        }
        return ApiResult.T();
    }

}
