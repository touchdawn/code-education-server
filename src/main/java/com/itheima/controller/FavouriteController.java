package com.itheima.controller;

import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.UserFavourite;
import com.itheima.service.CourseCommentVoteService;
import com.itheima.service.UserFavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fav")

public class FavouriteController {

    @Autowired
    UserFavouriteService userFavouriteService;

    @GetMapping("/getFavouriteCourseByUserId/{userId}")
    public ApiResult getFavouriteCourseByUserId(@PathVariable Integer userId){
        return userFavouriteService.getFavouriteCourseByUserId(userId);
    }

    @GetMapping("/getFavouriteNumberByCourseId/{courseId}")
    public ApiResult getFavouriteNumberByCourseId(@PathVariable Integer courseId){
        return ApiResult.T(userFavouriteService.getFavouriteNumberByCourseId(courseId));
    }

    @PostMapping("/addFavourite")
    public ApiResult addFavourite(@RequestBody Map<String, String> map){
        return userFavouriteService.addFavourite(map);
    }
}
