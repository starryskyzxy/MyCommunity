package com.zxy.mapper;

import com.zxy.model.Question;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionExtMapper {

    void incView(Question question);
}
