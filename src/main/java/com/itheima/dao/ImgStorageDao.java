package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.ImgStorage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper

public interface ImgStorageDao extends BaseMapper<ImgStorage> {

    @Select("select * from img_storage where LESSON_REL = #{courseId} and DELETE_FLAG = 1")
    ImgStorage findByCourseId(Integer courseId);
}
