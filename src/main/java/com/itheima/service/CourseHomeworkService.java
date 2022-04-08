package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.CourseHomework;
import com.itheima.domain.StudentHomeworkAnswer;

public interface CourseHomeworkService extends IService<CourseHomework> {

    ApiResult addHomework(CourseHomework courseHomework);

    ApiResult getHomeworkByHwId(Integer hwId);

    ApiResult getHomeworkByCourseId(Integer courseId, Integer userId);

    ApiResult checkAuthority(Integer courseId,Integer userID);
}
