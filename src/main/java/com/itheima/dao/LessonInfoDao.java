package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.LessonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface LessonInfoDao extends BaseMapper<LessonInfo> {
    @Select("select * from lesson_info " +
            "left join img_storage fs " +
            "on lesson_info.ID = fs.LESSON_REL " +
            "where lesson_info.STATUS = 1")
    List<Map<String,String>> getAllLessons();

    @Select("select * from lesson_info where CREATOR_ID=#{id} and STATUS = #{status} ORDER BY CREATE_AT desc ")
    List<LessonInfo> getAllByTeacherId(Integer id, Integer status);
}
