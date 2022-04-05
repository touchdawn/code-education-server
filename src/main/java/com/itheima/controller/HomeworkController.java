package com.itheima.controller;


import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.CourseHomework;
import com.itheima.domain.StudentHomeworkAnswer;
import com.itheima.service.CourseHomeworkService;
import com.itheima.service.StudentHomeworkAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/homework")
public class HomeworkController {

    @Autowired
    CourseHomeworkService courseHomeworkService;

    @Autowired
    StudentHomeworkAnswerService studentHomeworkAnswerService;

    @PostMapping("/addHomework")
    public ApiResult addHomework(@RequestBody CourseHomework courseHomework){
        return courseHomeworkService.addHomework(courseHomework);
    }

    @GetMapping("/getHomeworkByHwId/{hwId}")
    public ApiResult getHomeworkByHwId(@PathVariable Integer hwId){
        return courseHomeworkService.getHomeworkByHwId(hwId);
    }

    @GetMapping("/getHomeworkByCourseId/{courseId}")
    public ApiResult getHomeworkByCourseId(@PathVariable Integer courseId){
        return courseHomeworkService.getHomeworkByCourseId(courseId);
    }

    @PostMapping("/addStudentHomeworkAnswer")
    public ApiResult addStudentHomeworkAnswer(@RequestBody StudentHomeworkAnswer studentHomeworkAnswer){
        return studentHomeworkAnswerService.addStudentHomeworkAnswer(studentHomeworkAnswer);
    }

    @GetMapping("/checkAuthority/{courseId}/{userId}")
    public ApiResult checkAuthority(@PathVariable Integer courseId, @PathVariable Integer userId){
        return courseHomeworkService.checkAuthority(courseId,userId);
    }
}
