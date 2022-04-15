package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.LessonChapterSection;
import com.itheima.domain.LessonInfo;
import java.util.Map;

public interface LessonService extends IService<LessonInfo> {

    Boolean testT(Integer testInt);

    ApiResult getAllLessons();

    ApiResult getCourseInfo(Integer courseId, Integer userId, String type);

    ApiResult addNewCourse(Map<String, String> map);

    ApiResult getLessonByTeacherId(Integer id);

    ApiResult addNewSectionVideo(Map<String, String> map);

    ApiResult addNewChapter(Map<String, String> map);

    ApiResult searchLesson(String lessonName);

    ApiResult deleteSection(Integer sectionId);

    ApiResult getRandLessons(Integer lessonNumber);

    ApiResult addCourseLike(Integer courseId, Integer userId);

    ApiResult cancelCourseLike(Integer courseId, Integer userId);

    Boolean findCourseLike(Integer courseId, Integer userId);

    ApiResult addCourseDislike(Integer courseId, Integer userId);

    ApiResult cancelCourseDislike(Integer courseId, Integer userId);

    Boolean findCourseDislike(Integer courseId, Integer userId);

    Long getCourseLikeNum(Integer courseId);

    ApiResult addCourseSubscribe(Integer courseId, Integer userId);

    ApiResult cancelCourseSubscribe(Integer courseId, Integer userId);

    ApiResult getCourseSubscribeNum(Integer courseId);

    Boolean findCourseSubscribe(Integer courseId, Integer userId);

    ApiResult getSubCourseByUserId(Integer userId);

    ApiResult getCourseBySearch(Map searchForm);

    //审核章节
    ApiResult getCourseByQuery();

    ApiResult updateSectionStatus(LessonChapterSection lessonChapterSection,String auditOpinion);

    ApiResult getCourseByQueryPage(Map page);

//    ApiResult updateCourseById(LessonInfo lessonInfo);
}
