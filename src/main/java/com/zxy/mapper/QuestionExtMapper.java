package com.zxy.mapper;

import com.zxy.model.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionExtMapper {

    int incView(Question question);

    int incComment(Question question);

    List<Question> selectRelated(Question question);
}
