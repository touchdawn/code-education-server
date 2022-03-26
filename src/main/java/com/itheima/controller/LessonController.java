package com.itheima.controller;

import com.itheima.controller.utils.ApiResult;
import com.itheima.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/lesson")

public class LessonController {
    @Autowired
    private LessonService lessonService;

    @GetMapping("/getAllLessons")
    public ApiResult getAllLessons(){
        return lessonService.getAllLessons();
    }

    @GetMapping("/getCourseInfo/{courseId}")
    public ApiResult getCourseInfo(@PathVariable Integer courseId){
        return lessonService.getCourseInfo(courseId);
    }

    @PostMapping("/addNewCourse")
    public ApiResult addNewCourse(@RequestBody Map<String, String> map){
        return lessonService.addNewCourse(map);
    }
}
