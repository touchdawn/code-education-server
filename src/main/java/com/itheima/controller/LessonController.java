package com.itheima.controller;

import com.itheima.controller.utils.ApiResult;
import com.itheima.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lesson")

public class LessonController {
    @Autowired
    private LessonService lessonService;

    @GetMapping("/getAllLessons")
    public ApiResult getAllLessons(){

        return lessonService.getAllLessons();
    }
}
