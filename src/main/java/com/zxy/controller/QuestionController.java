package com.zxy.controller;

import com.zxy.dto.QuestionDTO;
import com.zxy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/questions/{id}")
    public String question(@PathVariable("id") Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.selectById(id);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }
}
