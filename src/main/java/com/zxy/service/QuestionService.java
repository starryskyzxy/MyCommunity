package com.zxy.service;

import com.zxy.dto.PageDTO;
import com.zxy.dto.QuestionDTO;
import com.zxy.mapper.QuestionExtMapper;
import com.zxy.mapper.QuestionMapper;
import com.zxy.mapper.UserMapper;
import com.zxy.model.Question;
import com.zxy.model.QuestionExample;
import com.zxy.model.User;
import com.zxy.model.UserExample;
import org.apache.ibatis.session.RowBounds;
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

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public List<QuestionDTO> selectAll() {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> questionList = questionMapper.selectByExample(new QuestionExample());
        for (Question question : questionList) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestion(question);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    //
    public PageDTO selectWithLimit(Integer page, Integer size) {
        int count = (int)questionMapper.countByExample(new QuestionExample());
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageContent(count,page,size);
        //实现代码的复用，我这个写法真的妙，嘿嘿
        Integer offset = size*(pageDTO.getCurrentPage()-1);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if (count == 0){
            offset = 0;
        }
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        for (Question question : questions) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestion(question);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOList);
        return pageDTO;
    }

    public Integer selectEndPage(Integer size){
        QuestionExample questionExample = new QuestionExample();
        int count = (int) questionMapper.countByExample(questionExample);
        int page;
        if (count % size == 0){
            page=count/size;
        }else{
            page = count/size+1;
        }
        if (count == 0){
            page = 1;
        }
        return page;
    }

    public PageDTO selectWithLimit(Integer id,Integer page,Integer size){
        QuestionExample questionExample = new QuestionExample();
        int count = (int)questionMapper.countByExample(questionExample);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageContent(count,page,size);
        //实现代码的复用，我这个写法真的妙，嘿嘿
        Integer currentPage = pageDTO.getCurrentPage();
        Integer offset = size*(currentPage -1);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if (count == 0){
            offset = 0;
        }
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria().andCreatorEqualTo(id);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample1, new RowBounds(offset, size));
        for (Question question : questions) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestion(question);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOList);
        return pageDTO;
    }

    public QuestionDTO selectById(Integer id){
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion(question);
        Integer creator = question.getCreator();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(creator);
        List<User> users = userMapper.selectByExample(userExample);
        questionDTO.setUser(users.get(0));
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question,questionExample);
        }
    }

    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        //递增的步长
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}
