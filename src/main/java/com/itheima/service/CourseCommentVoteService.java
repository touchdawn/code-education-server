package com.itheima.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.controller.utils.ApiResult;
import com.itheima.domain.CourseCommentVote;

import java.util.Map;

public interface CourseCommentVoteService extends IService<CourseCommentVote> {


    Integer getVoteNumberByCommentId(Integer courseId);

    ApiResult addVote(Map<String, String> map);
}
