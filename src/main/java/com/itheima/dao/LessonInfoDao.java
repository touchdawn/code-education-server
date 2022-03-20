package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.LessonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LessonInfoDao extends BaseMapper<LessonInfo> {
    @Select("select * from lesson_info")
    List<LessonInfo> getAllLessons();
}
