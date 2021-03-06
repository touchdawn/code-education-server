package com.itheima.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.StudentHomeworkAnswer;

public interface StudentHomeworkAnswerService extends IService<StudentHomeworkAnswer> {

    ApiResult addStudentHomeworkAnswer(StudentHomeworkAnswer studentHomeworkAnswer);

    ApiResult setStudentDone(Integer homeworkId, Integer studentId);

    ApiResult getStudentAnswer(Integer hwId, Integer userId);
}


