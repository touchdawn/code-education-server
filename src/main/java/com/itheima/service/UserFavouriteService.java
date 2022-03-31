package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.UserFavourite;

import java.util.Map;

public interface UserFavouriteService extends IService<UserFavourite> {

    Integer getFavouriteNumberByCourseId(Integer courseId);

    ApiResult addFavourite(Map<String, String> map);

    ApiResult getFavouriteCourseByUserId(Integer userId);
}
