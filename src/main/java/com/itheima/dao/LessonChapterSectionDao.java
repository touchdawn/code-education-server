package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.LessonChapterSection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper

public interface LessonChapterSectionDao extends BaseMapper<LessonChapterSection> {
    @Select("select * from lesson_chapter_section where LESSON_REL = #{courseId} and PARENT_ID = -1")
    List<Map> getCourseChapterInfo(Integer courseId);

//    @Select("select * from lesson_chapter_section lcs left join file_storage fs on fs.SECTION_REL = lcs.ID where lcs.PARENT_ID = #{parentId} and lcs.DELETE_FLAG = 0")
@Select("select * from lesson_chapter_section lcs left join file_storage fs on lcs.FILE_REL = fs.ID where lcs.PARENT_ID = #{parentId} and lcs.STATUS = 1")
List<Map> getChapterChildInfo(Integer parentId);
}
