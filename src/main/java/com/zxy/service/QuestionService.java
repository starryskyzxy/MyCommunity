package com.zxy.service;

import com.zxy.dto.PageDTO;
import com.zxy.dto.QuestionDTO;
import com.zxy.exception.CustomizeErrorCode;
import com.zxy.exception.CustomizeException;
import com.zxy.mapper.QuestionExtMapper;
import com.zxy.mapper.QuestionMapper;
import com.zxy.mapper.UserMapper;
import com.zxy.model.Question;
import com.zxy.model.QuestionExample;
import com.zxy.model.User;
import com.zxy.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
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

    public PageDTO selectWithLimit(Long id,Integer page,Integer size){
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

    public QuestionDTO selectById(Long id){
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion(question);
        Long creator = question.getCreator();
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
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setViewCount(0);
            questionMapper.insertSelective(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int i = questionMapper.updateByExampleSelective(question, questionExample);
            if (i != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        //递增的步长
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    //查找相关问题
    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO){
        String tagStr = questionDTO.getQuestion().getTag();
        //判断问题标签是否为空
        if (StringUtils.isBlank(tagStr)){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(tagStr, ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO1 = new QuestionDTO();
            questionDTO1.setQuestion(q);
            return questionDTO1;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
