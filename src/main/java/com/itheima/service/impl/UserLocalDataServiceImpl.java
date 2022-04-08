package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.UserLocalDataDao;
import com.itheima.domain.UserLocalData;
import com.itheima.service.UserLocalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserLocalDataServiceImpl extends ServiceImpl<UserLocalDataDao, UserLocalData> implements UserLocalDataService {

    @Autowired
    UserLocalDataDao userLocalDataDao;

    @Override
    public ApiResult getUserData(Integer userId) {
        UserLocalData userLocalData = userLocalDataDao.selectByUserId(userId);
        if (userLocalData == null){
            return ApiResult.F();
        } else {
            return ApiResult.T(userLocalData);
        }
    }

    @Override
    public ApiResult addUserData(UserLocalData userLocalData) {
        userLocalData.setUploadAt(new Date());
        userLocalData.setDeleteFlag(1);
        if (userLocalDataDao.selectByUserId(userLocalData.getUserId()) == null){
            save(userLocalData);
        } else {
            updateById(userLocalData);
        }
        return ApiResult.T();
    }
}
