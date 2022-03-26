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

    @Select("select password from user where email = #{email}")
    String getPassword(String email);

    @Select("select password from user where id = #{id}")
    String getPasswordById(Integer id);

    @Select("select phone from user where phone = #{phone}")
    Boolean checkUserPhoneExistOrNot(String phone);

    @Select("select email from user where email = #{email}")
    Boolean checkUserEmailExistOrNot(String email);

    @Select("select * from user where phone = #{phone}")
    User findByPhone (String phone);

    @Select("select * from user where email = #{email}")
    User findByEmail (String email);

    @Select("select * from user where id = #{id}")
    User selectById(Integer id);
}
