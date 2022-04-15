package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.User;

import java.util.Map;

public interface AdminService extends IService<User> {

    ApiResult getLessonsByPage(Map<String, String> map);
}
