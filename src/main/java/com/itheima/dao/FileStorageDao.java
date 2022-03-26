package com.itheima.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.FileStorage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileStorageDao extends BaseMapper<FileStorage> {

    @Select("select * from file_storage where URL = #{url}")
    FileStorage checkUrlExist(String url);

    @Select("select ID from file_storage where URL = #{url}")
    Integer findIdByUrl(String url);
}
