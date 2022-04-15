package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
            "       from lesson_info where STATUS = 1 \n" +
            "       order by SCORE desc, rand() LIMIT #{lessonNumber}")
    List<Map<String,String>> getRandLessons(Integer lessonNumber);

    @Select("select * from lesson_info where CREATOR_ID=#{id} and STATUS = #{status} ORDER BY CREATE_AT desc ")
    List<LessonInfo> getAllByTeacherId(Integer id, Integer status);

    @Select("select * from lesson_info where LESSON_NAME like % #{courseName} % and STATUS = 1")
    List<LessonInfo> getLikeByCourseName(String courseName);

    @Select("select lesson_info.ID AS id,\n" +
            "       lesson_info.LESSON_NAME AS lessonName,\n" +
            "       lesson_info.STATUS AS status,\n" +
            "       lesson_info.COURSE_TYPE AS courseType,\n" +
            "       lesson_info.SUBSCRIBE_NUM AS subscribeNum,\n" +
            "       lesson_info.SCORE AS score,\n" +
            "       lesson_info.CREATOR_ID AS creatorId,\n" +
            "       user.NAME AS creatorName,\n" +
            "       user.PHONE AS creatorPhone,\n" +
            "       user.EMAIL AS creatorEmail,\n" +
            "       lesson_info.COURSE_INTRODUCTION as courseIntroduction, \n" +
            "       lesson_info.CREATE_AT AS createAt,\n" +
            "       lesson_info.IMG_URL AS imgUrl\n" +
            "       from lesson_info left join user on lesson_info.CREATOR_ID = user.ID ")
//    IPage<SkuStock> selectPage(IPage<SkuStock> page, @Param("ew")Wrapper<SkuStock> queryWrapper);
    Page<Map> selectLessonPage(Page page);
}
