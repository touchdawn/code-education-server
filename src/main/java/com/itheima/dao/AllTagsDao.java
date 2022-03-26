package com.itheima.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.AllTags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AllTagsDao extends BaseMapper<AllTags> {

    @Select("select TAG_NAME from all_tags where PARENT_ID = -1" )
    List<String> getAllParentTags();

    @Select("select at1.TAG_NAME from all_tags at1 join all_tags at2 " +
            "on at1.PARENT_ID = at2.ID where at2.TAG_NAME = #{parentTagName}" )
    List<String> getByParentTag(String parentTagName);
}
