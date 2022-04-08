package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.UserLocalData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserLocalDataDao extends BaseMapper<UserLocalData> {
    @Select("select * from user_local_data where USER_ID = #{userId}")
    UserLocalData selectByUserId(Integer userId);
}
