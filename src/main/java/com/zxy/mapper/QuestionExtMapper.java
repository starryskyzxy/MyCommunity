package com.zxy.mapper;

import com.zxy.model.Question;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionExtMapper {

    int incView(Question record);
}
