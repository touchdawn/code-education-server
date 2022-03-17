package com.itheima.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

//@Mapper
//public interface UserDao {
//
//    @Select("select * from user where id = #{id}")
//    User getById(Integer id);
//}
@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("select password from user where phone = #{phone}")
    String getPassword(String phone);

    @Select("select phone from user where phone = #{phone}")
    Boolean checkUserPhoneExistOrNot(String phone);

    @Select("select email from user where email = #{email}")
    Boolean checkUserEmailExistOrNot(String email);

    @Select("select * from user where phone = #{phone}")
    User findByPhone (String phone);

}
