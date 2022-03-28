package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.UserComment;

import java.util.Map;

public interface UserCommentService extends IService<UserComment> {

    ApiResult addComment(Map<String, String> map);

    ApiResult getCommentListByCourseId(Integer courseId);

    ApiResult deleteComment(Integer commentId);
}
