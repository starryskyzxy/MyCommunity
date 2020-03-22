package com.zxy.controller;

import com.zxy.dto.PageDTO;
import com.zxy.mapper.UserMapper;
import com.zxy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    private static Integer size = 5;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping({"/","/index","/index.html"})
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page){
        PageDTO pageDTO = questionService.selectWithLimit(page,size);
        model.addAttribute("pageDTO",pageDTO);
        request.getSession().setAttribute("size",size);
        return "index";
    }
}
