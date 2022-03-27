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
        userCommentService.addComment(map);
        return ApiResult.F();
    }

    @GetMapping("/getComment/{courseId}")
    public ApiResult getComment(@PathVariable Integer courseId){
        return userCommentService.getCommentListByCourseId(courseId);
    }

}
