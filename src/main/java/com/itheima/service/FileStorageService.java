package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.FileStorage;

public interface FileStorageService extends IService<FileStorage> {

    /**
     * @param id
     * @return FileStorage
     */
    FileStorage getFileById(Integer id);


    /**
     * @param fileStorage
     * @return ApiResult
     */
    Boolean saveFile(FileStorage fileStorage) throws Exception;



}
