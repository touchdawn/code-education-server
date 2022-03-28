package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.UserComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserCommentDao extends BaseMapper<UserComment> {

//    @Select("select * from user_comment where COURSE_ID = #{courseId} and PARENT_ID = -1 and DELETE_FLAG = 1")
    @Select("select uc.ID as \"id\",\n" +
            "       uc.CONTENT as \"content\",\n" +
            "       user.NICK_NAME as \"nickName\",\n" +
            "       user.NAME as \"userName\",\n" +
            "       user.AVATAR as \"avatar\",\n" +
//            "       uc.CREATE_AT as \"createTime\",\n" +
//            "       uc.VOTES as \"votes\",\n" +
            "       DATE_FORMAT(uc.CREATE_AT, '%m-%d %H:%i') As \"createTime\"," +
            "       user.ID as \"userId\"\n" +
            "from user_comment uc left join user on uc.CREATOR_ID = user.ID " +
            "where COURSE_ID = #{courseId} and PARENT_ID = -1 and DELETE_FLAG = 1 " +
            "and user.STATUS = 1 order by CREATE_AT desc")
    List<Map> getCommentListByCourseId(Integer courseId);

//    @Select("select * from user_comment where PARENT_ID = #{parentId} and DELETE_FLAG = 1")
    @Select("select uc.ID as \"id\",\n" +
            "       uc.CONTENT as \"content\",\n" +
            "       user.NICK_NAME as \"nickName\",\n" +
            "       user.NAME as \"userName\",\n" +
            "       user.AVATAR as \"avatar\",\n" +
//            "       uc.CREATE_AT as \"createTime\",\n" +
            "DATE_FORMAT(uc.CREATE_AT, '%m-%d %H:%i') As \"createTime\"," +
//            "       uc.VOTES as \"votes\",\n" +
            "       user.ID as \"userId\"\n" +
            "from user_comment uc left join user on uc.CREATOR_ID = user.ID " +
            "where PARENT_ID = #{parentId} and DELETE_FLAG = 1 " +
            "and user.STATUS = 1 order by CREATE_AT desc")
    List<Map> getChildCommentByParentId(Integer parentId);

    @Select("UPDATE user_comment\n" +
            "SET DELETE_FLAG = 0\n" +
            "WHERE PARENT_ID =#{parentId}")
    void deleteChildByParentId(Integer parentId);
}
