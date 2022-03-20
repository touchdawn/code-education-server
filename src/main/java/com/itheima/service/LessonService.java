package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.LessonInfo;

public interface LessonService extends IService<LessonInfo> {

    Boolean testT(Integer testInt);

    ApiResult getAllLessons();
}
