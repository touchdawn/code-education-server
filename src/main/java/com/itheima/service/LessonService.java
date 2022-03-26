package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.LessonInfo;
import java.util.Map;

public interface LessonService extends IService<LessonInfo> {

    Boolean testT(Integer testInt);

    ApiResult getAllLessons();

    ApiResult getCourseInfo(Integer courseId);

    ApiResult addNewCourse(Map<String, String> map);
}
