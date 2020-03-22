package com.zxy.mapper;

import com.zxy.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    void insert(User user);

    User selectByToken(@Param("token") String token);

    User selectById(@Param("id") int id);
}
