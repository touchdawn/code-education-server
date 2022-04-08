package com.itheima.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.StudentHomeworkResult;

public interface StudentHomeworkResultService extends IService<StudentHomeworkResult> {
    ApiResult getHwStatistic(Integer hwId);
}
