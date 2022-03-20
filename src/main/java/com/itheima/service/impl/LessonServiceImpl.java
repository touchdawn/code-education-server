package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.LessonInfoDao;
import com.itheima.domain.LessonInfo;
import com.itheima.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl extends ServiceImpl<LessonInfoDao, LessonInfo> implements LessonService {

    @Autowired
    private LessonInfoDao lessonInfoDao;

    @Override
    public Boolean testT(Integer testInt){
        return false;
    }

    @Override
    public ApiResult getAllLessons() {
        List<LessonInfo> allLessons = lessonInfoDao.getAllLessons();
        return ApiResult.T(allLessons);
    }


}
