package com.zxy.service;

import com.zxy.dto.PageDTO;
import com.zxy.dto.QuestionDTO;
import com.zxy.mapper.QuestionMapper;
import com.zxy.mapper.UserMapper;
import com.zxy.model.Question;
import com.zxy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> selectAll() {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> questions = questionMapper.selectAll();
        for (Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestion(question);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    //
    public PageDTO selectWithLimit(Integer page, Integer size) {
        int count = questionMapper.count();
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageContent(count,page,size);
        //实现代码的复用，我这个写法真的妙，嘿嘿
        Integer offset = size*(pageDTO.getCurrentPage()-1);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if (count == 0){
            offset = 0;
        }
        List<Question> questions = questionMapper.selectWithLimit(offset,size);
        for (Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestion(question);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOList);
        return pageDTO;
    }

    public Integer selectEndPage(Integer size){
        int count = questionMapper.count();
        int page;
        if (count % size == 0){
            page=count/size;
        }else{
            page = count/size+1;
        }
        return page;
    }

    public PageDTO selectWithLimit(Integer id,Integer page,Integer size){
        int count = questionMapper.countByUserId(id);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageContent(count,page,size);
        //实现代码的复用，我这个写法真的妙，嘿嘿
        Integer currentPage = pageDTO.getCurrentPage();
        Integer offset = size*(currentPage -1);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if (count == 0){
            offset = 0;
        }
        List<Question> questions = questionMapper.selectUserQuestion(id,offset,size);
        for (Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestion(question);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOList);
        return pageDTO;
    }

    public QuestionDTO selectById(Integer id){
        Question question = questionMapper.selectById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion(question);
        Integer creator = questionMapper.selectCreatorById(id);
        User user = userMapper.selectById(creator);
        questionDTO.setUser(user);
        return questionDTO;
    }
}
