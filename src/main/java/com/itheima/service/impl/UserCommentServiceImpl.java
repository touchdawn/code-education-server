package com.itheima.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.controller.utils.ApiResult;
import com.itheima.dao.UserCommentDao;
import com.itheima.domain.UserComment;
import com.itheima.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserCommentServiceImpl extends ServiceImpl<UserCommentDao,UserComment> implements UserCommentService {

    @Autowired
    UserCommentDao userCommentDao;

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
    public ApiResult getCommentListByCourseId(Integer courseId) {
        List<Map> parentCommentList = userCommentDao.getCommentListByCourseId(courseId);
//        Map map = new HashMap<>();
//        map.put("commentList",parentCommentList);

        List<Object> commentList = new ArrayList<>();
        parentCommentList.forEach(obj->{
            Integer parentId =(Integer) obj.get("id");
            List<Map> childComment = userCommentDao.getChildCommentByParentId(parentId);
            if (childComment != null) {
                obj.put("child",childComment);
            }
            commentList.add(obj);
        });
        return ApiResult.T(commentList);
    }


}
