package com.itheima.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.LessonInfoDao;
import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.service.AdminService;
import com.itheima.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.lang.Integer.parseInt;

@Service
public class AdminServiceImpl extends ServiceImpl<UserDao, User> implements AdminService {
    @Autowired
    LessonService lessonService;

    @Autowired
    LessonInfoDao lessonInfoDao;

    @Override
    public ApiResult getLessonsByPage(Map<String, String> map) {
        int current = parseInt(map.get("current"));
        int size = parseInt(map.get("size"));
        Page page = new Page(current,size);

        Page<Map> iPage = lessonInfoDao.selectLessonPage(page);
//        IPage iPage = lessonInfoDao.selectPage(page, null);
        return ApiResult.T(iPage);
    }

}
