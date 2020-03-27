package com.zxy.controller;

import com.zxy.dto.CommentCreateDTO;
import com.zxy.dto.CommentDTO;
import com.zxy.dto.QuestionDTO;
import com.zxy.service.CommentService;
import com.zxy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/questions/{id}")
    public String question(@PathVariable("id") Long id, Model model){
        QuestionDTO questionDTO = questionService.selectById(id);

        List<CommentDTO> commentDTOList = commentService.selectByQuestionId(id);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("commentDTOList",commentDTOList);
        return "question";
    }

   
}
