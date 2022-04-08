package com.itheima.controller;


import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.CourseHomework;
import com.itheima.domain.StudentHomeworkAnswer;
import com.itheima.service.CourseHomeworkService;
import com.itheima.service.StudentHomeworkAnswerService;
import com.itheima.service.StudentHomeworkResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/homework")
public class HomeworkController {

    @Autowired
    CourseHomeworkService courseHomeworkService;

    @Autowired
    StudentHomeworkAnswerService studentHomeworkAnswerService;

    @Autowired
    StudentHomeworkResultService studentHomeworkResultService;

    @PostMapping("/addHomework")
    public ApiResult addHomework(@RequestBody CourseHomework courseHomework){
        return courseHomeworkService.addHomework(courseHomework);
    }

    @GetMapping("/getHomeworkByHwId/{hwId}")
    public ApiResult getHomeworkByHwId(@PathVariable Integer hwId){
        return courseHomeworkService.getHomeworkByHwId(hwId);
    }

    @GetMapping("/getHomeworkByCourseId/{courseId}/{userId}")
    public ApiResult getHomeworkByCourseId(@PathVariable Integer courseId,@PathVariable Integer userId){
        return courseHomeworkService.getHomeworkByCourseId(courseId,userId);
    }

    @PostMapping("/addStudentHomeworkAnswer")
    public ApiResult addStudentHomeworkAnswer(@RequestBody StudentHomeworkAnswer studentHomeworkAnswer){
        return studentHomeworkAnswerService.addStudentHomeworkAnswer(studentHomeworkAnswer);
    }

    @GetMapping("/getStudentAnswer/{hwId}/{userId}")
    public ApiResult getStudentAnswer(@PathVariable Integer hwId,@PathVariable Integer userId){
        return studentHomeworkAnswerService.getStudentAnswer(hwId,userId);
    }

    @GetMapping("/checkAuthority/{courseId}/{userId}")
    public ApiResult checkAuthority(@PathVariable Integer courseId, @PathVariable Integer userId){
        return courseHomeworkService.checkAuthority(courseId,userId);
    }

    @GetMapping("/getHwStatistic/{hwId}")
    public ApiResult getHwStatistic(@PathVariable Integer hwId){
        return studentHomeworkResultService.getHwStatistic(hwId);
    }

    @GetMapping("/redisTest/{hwId}/{userId}")
    public ApiResult redisTest(@PathVariable Integer hwId, @PathVariable Integer userId){
        return studentHomeworkAnswerService.setStudentDone(hwId,userId);
    }
}
