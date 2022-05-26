package com.itheima.controller;


import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.LessonChapterSection;
import com.itheima.domain.LessonInfo;
import com.itheima.service.AdminService;
import com.itheima.service.LessonService;
import com.itheima.service.TeacherApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private TeacherApplicationService teacherApplicationService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private LessonService lessonService;

    @PostMapping("/addTeacherApplication")
    public ApiResult addTeacherApplication(@RequestBody Map<String, String> map){
        return teacherApplicationService.addNewApplication(map);
    }

    @GetMapping("/getTeacherApplication")
    public ApiResult getTeacherApplication(){
        return teacherApplicationService.getTeacherApplication();
    }

    @GetMapping("/approveTeacherApplication/{applyId}")
    public ApiResult approveTeacherApplication(@PathVariable Integer applyId){
        return teacherApplicationService.approveTeacherApplication(applyId);
    }

    @GetMapping("/disApproveTeacherApplication/{applyId}")
    public ApiResult disApproveTeacherApplication(@PathVariable Integer applyId){
        return teacherApplicationService.disApproveTeacherApplication(applyId);
    }

    @PostMapping("/getLessonsByPage")
    public ApiResult getLessonsByPage(@RequestBody Map<String,String> map){
        return adminService.getLessonsByPage(map);
    };

    @PostMapping("/updateCourse")
    public ApiResult update(@RequestBody LessonInfo lessonInfo){
        //如果封禁课的话会移除首页推荐
        if (lessonInfo.getStatus() == 0){
            lessonInfo.setScore(0);
        }
        return ApiResult.T(lessonService.updateById(lessonInfo));
    }
/////////////////////搜索课
    @PostMapping("/searchCourse")
    public ApiResult searchCourse(@RequestBody Map searchForm){
        return lessonService.getCourseBySearch(searchForm);
    }

    //获取审核章节
    @PostMapping("/getCourseByQuery")
    public ApiResult getCourseByQuery(){
        return lessonService.getCourseByQuery();
    }

    //获取审核章节
    @PostMapping("/getCourseByQueryPage")
    public ApiResult getCourseByQueryPage(@RequestBody Map page){
        return lessonService.getCourseByQueryPage(page);
    }

    //审核章节
    @PostMapping("/updateSectionStatus")
    public ApiResult updateSectionStatus(@RequestBody LessonChapterSection lessonChapterSection,@RequestParam(value = "auditOpinion") String auditOpinion){
        return lessonService.updateSectionStatus(lessonChapterSection, auditOpinion);
    }

}
