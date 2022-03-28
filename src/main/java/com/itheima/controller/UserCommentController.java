package com.itheima.controller;


import com.itheima.controller.utils.ApiResult;
import com.itheima.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comment")
public class UserCommentController {
    @Autowired
    UserCommentService userCommentService;

    @PostMapping("/addComment")
    public ApiResult addComment(@RequestBody Map<String, String> map){
        return userCommentService.addComment(map);
    }

    @GetMapping("/getComment/{courseId}/{userId}")
    public ApiResult getComment(@PathVariable Integer courseId, @PathVariable Integer userId){
        return userCommentService.getCommentListByCourseId(courseId,userId);
    }

    @GetMapping("/deleteComment/{commentId}")
    public ApiResult deleteComment(@PathVariable Integer commentId){
        return userCommentService.deleteComment(commentId);
    }

}
