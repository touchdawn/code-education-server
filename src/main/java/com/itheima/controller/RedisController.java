package com.itheima.controller;

import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.AllTags;
import com.itheima.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")

public class RedisController {

    @Autowired
    private LessonService lessonService;

    ///点赞
    @GetMapping("/addCourseLike/{courseId}/{userId}")
    public ApiResult addCourseLike(@PathVariable Integer courseId, @PathVariable Integer userId){
        return lessonService.addCourseLike(courseId,userId);
    }

    @GetMapping("/cancelCourseLike/{courseId}/{userId}")
    public ApiResult cancelCourseLike(@PathVariable Integer courseId, @PathVariable Integer userId){
        return lessonService.cancelCourseLike(courseId,userId);
    }

    @GetMapping("/findCourseLike/{courseId}/{userId}")
    public ApiResult findCourseLike(@PathVariable Integer courseId, @PathVariable Integer userId){
        return ApiResult.T(lessonService.findCourseLike(courseId,userId));
    }

    ///点踩
    @GetMapping("/addCourseDislike/{courseId}/{userId}")
    public ApiResult addCourseDislike(@PathVariable Integer courseId, @PathVariable Integer userId){
        return lessonService.addCourseDislike(courseId,userId);
    }

    @GetMapping("/cancelCourseDislike/{courseId}/{userId}")
    public ApiResult cancelCourseDislike(@PathVariable Integer courseId, @PathVariable Integer userId){
        return lessonService.cancelCourseDislike(courseId,userId);
    }

    @GetMapping("/findCourseDislike/{courseId}/{userId}")
    public ApiResult findCourseDislike(@PathVariable Integer courseId, @PathVariable Integer userId){
        return ApiResult.T(lessonService.findCourseDislike(courseId,userId));
    }

    @GetMapping("/getCourseLikeNum/{courseId}")
    public ApiResult getCourseLikeNum(@PathVariable Integer courseId){
        return ApiResult.T(lessonService.getCourseLikeNum(courseId));
    }

    ///订阅
    @GetMapping("/addCourseSubscribe/{courseId}/{userId}")
    public ApiResult addCourseSubscribe(@PathVariable Integer courseId, @PathVariable Integer userId){
        return lessonService.addCourseSubscribe(courseId,userId);
    }

    @GetMapping("/cancelCourseSubscribe/{courseId}/{userId}")
    public ApiResult cancelCourseSubscribe(@PathVariable Integer courseId, @PathVariable Integer userId){
        return lessonService.cancelCourseSubscribe(courseId,userId);
    }

    //查看这个用户是否订阅了这门课
    @GetMapping("/findCourseSubscribe/{courseId}/{userId}")
    public ApiResult findCourseSubscribe(@PathVariable Integer courseId, @PathVariable Integer userId){
        return ApiResult.T(lessonService.findCourseSubscribe(courseId,userId));
    }

    @GetMapping("/getCourseSubscribeNum/{courseId}")
    public ApiResult getCourseSubscribeNum(@PathVariable Integer courseId){
        return lessonService.getCourseSubscribeNum(courseId);
    }

    @GetMapping("/getSubCourseByUserId/{userId}")
    public ApiResult getSubCourseByUserId( @PathVariable Integer userId){
        return lessonService.getSubCourseByUserId(userId);
    }
}
