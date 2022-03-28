package com.itheima.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.CourseCommentVoteDao;
import com.itheima.domain.CourseCommentVote;
import com.itheima.service.CourseCommentVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
public class CourseCommentVoteServiceImpl extends ServiceImpl<CourseCommentVoteDao, CourseCommentVote> implements CourseCommentVoteService {

    @Autowired CourseCommentVoteDao courseCommentVoteDao;

    @Override
    public Integer getVoteNumberByCommentId(Integer commentId) {

        return courseCommentVoteDao.getVoteNumberByCommentId(commentId);
    }

    @Override
    public ApiResult addVote(Map<String, String> map) {
        Integer userId = Integer.parseInt(map.get("userId"));
        Integer commentId = Integer.parseInt(map.get("commentId"));
        Integer courseId = Integer.parseInt(map.get("courseId"));
        Integer voteId = Integer.parseInt(map.get("voteId")); //没点赞过就为-1
//        String voteHistory = map.get("voteHistory");
        String action = map.get("action");

        if (Objects.equals(action, "vote")){
            CourseCommentVote courseCommentVote = new CourseCommentVote();
            courseCommentVote.setCommentId(commentId);
            courseCommentVote.setVoterId(userId);
            courseCommentVote.setCreateAt(new Date());
            courseCommentVote.setCourseId(courseId);
            courseCommentVote.setDeleteFlag(1);
            save(courseCommentVote);
            Integer voteIdReturn = courseCommentVoteDao.findIdByCommentIdAndVoterId(commentId, userId);
            return ApiResult.T(voteIdReturn);

        } else if (Objects.equals(action, "delete")) {
            courseCommentVoteDao.deleteById(voteId);
            return ApiResult.T("deleteSuccess");
        }
        return ApiResult.F();

    }
}
