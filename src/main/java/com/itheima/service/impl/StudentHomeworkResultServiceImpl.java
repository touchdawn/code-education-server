package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.CourseHomeworkDao;
import com.itheima.dao.StudentHomeworkResultDao;
import com.itheima.domain.CourseHomework;
import com.itheima.domain.StudentHomeworkResult;
import com.itheima.service.StudentHomeworkResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentHomeworkResultServiceImpl extends ServiceImpl<StudentHomeworkResultDao, StudentHomeworkResult> implements StudentHomeworkResultService {

    @Autowired
    StudentHomeworkResultDao studentHomeworkResultDao;

    @Autowired
    CourseHomeworkDao courseHomeworkDao;

    @Override
    public ApiResult getHwStatistic(Integer hwId) {
        List<StudentHomeworkResult> homeworkResult = studentHomeworkResultDao.getHomeworkResult(hwId);
        CourseHomework courseHomework = courseHomeworkDao.selectById(hwId);
        Map map = new HashMap<>();
        map.put("statisticData",homeworkResult);
        map.put("homeworkData",courseHomework);
//        List<Map> maps = studentHomeworkResultDao.teacherGetHomeworkResult(hwId);
        return ApiResult.T(map);
    }
}
