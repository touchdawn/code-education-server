package com.itheima.controller;


import com.itheima.controller.utils.ApiResult;
import com.itheima.service.TeacherApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private TeacherApplicationService teacherApplicationService;

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
}
