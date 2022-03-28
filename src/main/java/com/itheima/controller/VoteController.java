package com.itheima.controller;


import com.itheima.controller.utils.ApiResult;
import com.itheima.service.CourseCommentVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    CourseCommentVoteService courseCommentVoteService;
    @GetMapping("/getVoteNumberByCommentId/{courseId}")
    public ApiResult getVoteNumberByCommentId(@PathVariable Integer courseId){
        return ApiResult.T(courseCommentVoteService.getVoteNumberByCommentId(courseId));
    }

    @PostMapping("/addVote")
    public ApiResult addVote(@RequestBody Map<String, String> map){

        return courseCommentVoteService.addVote(map);
    }
}
