package com.zxy.mapper;

import com.zxy.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    int insert(Question question);

    int count();

    List<Question> selectAll();

    List<Question> selectWithLimit(@Param("page") Integer page, @Param("size") Integer size);

    List<Question> selectUserQuestion(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);

    int countByUserId(@Param("userId")Integer userId);

    Question selectById(@Param("id") Integer id);

    Integer selectCreatorById(@Param("id") Integer id);
}
