package com.itheima.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.CourseCommentVote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CourseCommentVoteDao extends BaseMapper<CourseCommentVote> {

    @Select("select count(1) from course_comment_vote " +
            "where COMMENT_ID = #{commentId} and DELETE_FLAG = 1")
    Integer getVoteNumberByCommentId(Integer commentId);


    @Select("select * from course_comment_vote where COMMENT_ID = #{commentId}  and DELETE_FLAG = 1 and VOTER_ID =  #{userId}")
    CourseCommentVote findVoteRecordByUserIdAndCommentId(Integer userId, Integer commentId);

    @Select("select ID from course_comment_vote where COMMENT_ID = #{commentId} and VOTER_ID =  #{voterId}")
    Integer findIdByCommentIdAndVoterId(Integer commentId, Integer voterId);
}
