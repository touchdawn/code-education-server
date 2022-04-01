package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.TeacherApplication;

import java.util.Map;

public interface TeacherApplicationService extends IService<TeacherApplication> {

    ApiResult addNewApplication(Map<String, String> map);

    ApiResult getTeacherApplication();

    ApiResult approveTeacherApplication(Integer applyId);

    ApiResult disApproveTeacherApplication(Integer applyId);
}
