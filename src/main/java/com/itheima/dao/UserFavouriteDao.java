package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.UserFavourite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserFavouriteDao extends BaseMapper<UserFavourite> {

    @Select("select COURSE_INTRODUCTION AS courseIntroduction,\n" +
            "       COURSE_TYPE AS courseType,\n" +
//            "       li.CREATE_AT AS createAt,\n" +
            "       DATE_FORMAT(uf.CREATE_AT, '%m-%d %H:%i') As \"createAt\"," +
            "       li.CREATOR_ID AS creatorId,\n" +
            "       li.ID AS id,\n" +
            "       uf.ID AS favId,\n" +
            "       IMG_URL AS imgUrl,\n" +
            "       LESSON_NAME AS lessonName\n" +
            "from user_favourite uf\n" +
            "left join lesson_info li on uf.COURSE_ID = li.ID\n" +
            "where uf.CREATOR_ID = #{userId} and li.STATUS = 1\n" +
            "order by uf.CREATE_AT desc ")
    List<Map<String,String>> getFavListByUserId(Integer userId);

    @Select("select count(1) from user_favourite " +
            "where COURSE_ID = #{courseId} ")
    Integer getFavNumberByCourseId(Integer courseId);

    @Select("select * from user_favourite where CREATOR_ID =  #{userId} and COURSE_ID = #{courseId} ")
    UserFavourite findFavouriteRecordByUserIdAndCourseId(Integer userId, Integer courseId);

    @Select("select ID from user_favourite where COURSE_ID = #{courseId} and CREATOR_ID = #{creatorId}")
    Integer findIdByCourseIdAndCreatorId(Integer courseId, Integer creatorId);

}
