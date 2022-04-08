package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.UserLocalData;

public interface UserLocalDataService extends IService<UserLocalData> {
    ApiResult getUserData(Integer userId);

    ApiResult addUserData(UserLocalData userLocalData);
}
