package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.LessonInfo;
import java.util.Map;

public interface LessonService extends IService<LessonInfo> {

    Boolean testT(Integer testInt);

    ApiResult getAllLessons();

    ApiResult getCourseInfo(Integer courseId, Integer userId);

    ApiResult addNewCourse(Map<String, String> map);

    ApiResult getLessonByTeacherId(Integer id);

    ApiResult addNewSectionVideo(Map<String, String> map);

    ApiResult addNewChapter(Map<String, String> map);

    ApiResult searchLesson(String lessonName);

    ApiResult deleteSection(Integer sectionId);
}
