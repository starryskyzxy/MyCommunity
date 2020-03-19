package com.zxy.mapper;

import com.zxy.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    void insert(User user);


}
