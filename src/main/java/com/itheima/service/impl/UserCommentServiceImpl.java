package com.itheima.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.CourseCommentVoteDao;
import com.itheima.dao.UserCommentDao;
import com.itheima.domain.CourseCommentVote;
import com.itheima.domain.UserComment;
import com.itheima.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserCommentServiceImpl extends ServiceImpl<UserCommentDao,UserComment> implements UserCommentService {

    @Autowired
    UserCommentDao userCommentDao;

    @Autowired
    CourseCommentVoteDao courseCommentVoteDao;

    @Override
    public ApiResult addComment(Map<String, String> map) {
        Integer creatorId = Integer.parseInt( map.get("creatorId"));
        Integer courseId = Integer.parseInt( map.get("courseId"));
        Integer parentId = Integer.parseInt( map.get("parentId"));
        String content =  map.get("comment");

        UserComment userComment = new UserComment();
        userComment.setContent(content);
        userComment.setCreateAt(new Date());
        userComment.setCourseId(courseId);
        userComment.setCreatorId(creatorId);
        userComment.setParentId(parentId);
        save(userComment);
        return ApiResult.T();
    }

    @Override
    public ApiResult getCommentListByCourseId(Integer courseId, Integer userId) {
        List<Map> parentCommentList = userCommentDao.getCommentListByCourseId(courseId);
//        Map map = new HashMap<>();
//        map.put("commentList",parentCommentList);

        parentCommentList.forEach(map->{
//            Integer userId = (Integer) map.get("userId");
            Integer id = (Integer) map.get("id");
            CourseCommentVote voteRecordByUserIdAndCommentId = courseCommentVoteDao.findVoteRecordByUserIdAndCommentId(userId, id);
            if (voteRecordByUserIdAndCommentId != null){
                if (voteRecordByUserIdAndCommentId.getDeleteFlag() == 1){
                    map.put("voted",true);
                    map.put("voteHistory",true);
                } else if (voteRecordByUserIdAndCommentId.getDeleteFlag() == 0){
                    map.put("voted",false);
                    map.put("voteHistory",true);
                }
                map.put("voteId",voteRecordByUserIdAndCommentId.getId());
            } else {
                map.put("voted",false);
                map.put("voteHistory",false);
                map.put("voteId","-1");
            }
            Integer voteNumber = courseCommentVoteDao.getVoteNumberByCommentId(id);
            map.put("voteNumber",voteNumber);
        });

        List<Object> commentList = new ArrayList<>();
        parentCommentList.forEach(obj->{
            Integer parentId =(Integer) obj.get("id");
            List<Map> childComment = userCommentDao.getChildCommentByParentId(parentId);
            if (childComment != null) {
                //子评论暂时不设置点赞
//                childComment.forEach(child->{
//                    Integer id = (Integer) child.get("id");
//                    CourseCommentVote voteRecordByUserIdAndCommentIdChild = courseCommentVoteDao.findVoteRecordByUserIdAndCommentId(userId, id);
//                    if (voteRecordByUserIdAndCommentIdChild != null){
//                        if (voteRecordByUserIdAndCommentIdChild.getDeleteFlag() == 1){
//                            child.put("voted","true");
//                            child.put("voteHistory","true");
//                        } else if (voteRecordByUserIdAndCommentIdChild.getDeleteFlag() == 0){
//                            child.put("voted","false");
//                            child.put("voteHistory","true");
//                        }
//                    } else {
//                        child.put("voted","false");
//                        child.put("voteHistory","false");
//                    }
//                });
                obj.put("child",childComment);
            }
            commentList.add(obj);
        });
        return ApiResult.T(commentList);
    }

    @Override
    public ApiResult deleteComment(Integer commentId) {
        UserComment userComment = userCommentDao.selectById(commentId);
        if (userComment != null) {
            userComment.setDeleteFlag(0);
            userCommentDao.updateById(userComment);
            if(userComment.getParentId() == -1){ //如果删的是父评论
                userCommentDao.deleteChildByParentId(commentId);
            }
            return ApiResult.T();
        }
        return ApiResult.F("","找不到该评论！");
    }


}
