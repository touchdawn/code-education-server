package com.itheima.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.FileStorageDao;
import com.itheima.domain.FileStorage;
import com.itheima.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FileStorageImpl extends ServiceImpl<FileStorageDao, FileStorage> implements FileStorageService {
    @Autowired
    private FileStorageDao fileStorageDao;

    @Override
    public FileStorage getFileById(Integer id) {
        return fileStorageDao.selectById(id);
    }

    @Override
    public Boolean saveFile(FileStorage fileStorage) throws Exception{
        FileStorage f = fileStorageDao.checkUrlExist(fileStorage.getUrl());
        if (f == null) {
            try {
                fileStorage.setCreatedAt(new Date());
                int insertResult = fileStorageDao.insert(fileStorage);
            } catch (Exception e) {
                throw new Exception("sss");
            }
            return true;
        } else {
            throw new Exception("sss") ;
        }
    }
}
