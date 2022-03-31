package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.UserFavouriteDao;
import com.itheima.domain.CourseCommentVote;
import com.itheima.domain.UserFavourite;
import com.itheima.service.UserFavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserFavouriteServiceImpl extends ServiceImpl<UserFavouriteDao, UserFavourite> implements UserFavouriteService {

    @Autowired
    UserFavouriteDao userFavouriteDao;

    @Override
    public Integer getFavouriteNumberByCourseId(Integer courseId) {
        return userFavouriteDao.getFavNumberByCourseId(courseId);
    }

    @Override
    public ApiResult addFavourite(Map<String, String> map) {
        Integer creatorId = Integer.parseInt(map.get("userId"));
        Integer courseId = Integer.parseInt(map.get("courseId"));
        Integer favouriteId = Integer.parseInt(map.get("favouriteId")); //没收藏就为-1
        String action = map.get("action");

        if (Objects.equals(action, "add")){
            UserFavourite userFavourite = new UserFavourite();
            userFavourite.setCreatorId(creatorId);
            userFavourite.setCreateAt(new Date());
            userFavourite.setCourseId(courseId);
            save(userFavourite);
            Integer userFavouriteIdReturn = userFavouriteDao.findIdByCourseIdAndCreatorId(courseId, creatorId);
            return ApiResult.T(userFavouriteIdReturn);

        } else if (Objects.equals(action, "delete")) {
            userFavouriteDao.deleteById(favouriteId);
            return ApiResult.T("deleteSuccess");
        }
        return ApiResult.F();
    }

    @Override
    public ApiResult getFavouriteCourseByUserId(Integer userId) {
        List<Map<String, String>> favListByUserId = userFavouriteDao.getFavListByUserId(userId);
        if (favListByUserId != null){
            return ApiResult.T(favListByUserId);
        } else {
            return ApiResult.T("noResult");
        }
    }
}
