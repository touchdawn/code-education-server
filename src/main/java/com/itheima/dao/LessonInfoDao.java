package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.LessonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface LessonInfoDao extends BaseMapper<LessonInfo> {
//    @Select("select * from lesson_info " +
//            "left join img_storage fs " +
//            "on lesson_info.ID = fs.LESSON_REL " +
//            "where lesson_info.STATUS = 1")
    @Select("select ID AS lessonId,\n" +
            "       LESSON_NAME AS lessonName,\n" +
            "       CREATOR_ID AS creatorId,\n" +
            "       COURSE_INTRODUCTION as courseIntroduction, \n" +
            "       CREATE_AT AS createAt,\n" +
            "       IMG_URL AS imgUrl\n" +
            "       from lesson_info  where STATUS = 1")
    List<Map<String,String>> getAllLessons();

    @Select("select ID AS lessonId, \n" +
            "       LESSON_NAME AS lessonName, \n" +
            "       CREATOR_ID AS creatorId, \n" +
            "       COURSE_INTRODUCTION as courseIntroduction, \n" +
            "       CREATE_AT AS createAt, \n" +
            "       IMG_URL AS imgUrl \n" +
            "       from lesson_info  where STATUS = 1 \n" +
            "       order by rand() LIMIT #{lessonNumber}")
    List<Map<String,String>> getRandLessons(Integer lessonNumber);

    @Select("select * from lesson_info where CREATOR_ID=#{id} and STATUS = #{status} ORDER BY CREATE_AT desc ")
    List<LessonInfo> getAllByTeacherId(Integer id, Integer status);

    @Select("select * from lesson_info where LESSON_NAME like % #{courseName} % and STATUS = 1")
    List<LessonInfo> getLikeByCourseName(String courseName);

}
