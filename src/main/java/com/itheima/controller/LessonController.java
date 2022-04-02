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

    @GetMapping("/getRandLessons/{lessonNumber}")
    public ApiResult getRandLessons(@PathVariable Integer lessonNumber){
        return lessonService.getRandLessons(lessonNumber);
    }

    @GetMapping("/searchLesson/{lessonName}")
    public ApiResult searchLesson(@PathVariable String lessonName){
        return lessonService.searchLesson(lessonName);
    }

    @GetMapping("/getLessonByTeacherId/{id}")
    public ApiResult getLessonByTeacherId(@PathVariable Integer id){
        return lessonService.getLessonByTeacherId(id);
    }

    @GetMapping("/getCourseInfo/{courseId}/{userId}")
    public ApiResult getCourseInfo(@PathVariable Integer courseId, @PathVariable Integer userId){
        return lessonService.getCourseInfo(courseId,userId,"available");
    }

    @GetMapping("/getCourseAllInfo/{courseId}/{userId}")
    public ApiResult getCourseAllInfo(@PathVariable Integer courseId, @PathVariable Integer userId){
        return lessonService.getCourseInfo(courseId,userId,"all");
    }

    @PostMapping("/addNewCourse")
    public ApiResult addNewCourse(@RequestBody Map<String, String> map){
        return lessonService.addNewCourse(map);
    }

    @PostMapping("/addNewChapter")
    public ApiResult addNewChapter(@RequestBody Map<String, String> map){
        return lessonService.addNewChapter(map);
    }

    @PostMapping("/addNewSectionVideo")
    public ApiResult addNewSectionVideo(@RequestBody Map<String, String> map){
        return lessonService.addNewSectionVideo(map);
    }

    @GetMapping("/deleteSection/{sectionId}")
    public ApiResult deleteSection(@PathVariable Integer sectionId) {
        return lessonService.deleteSection(sectionId);
    }
}
