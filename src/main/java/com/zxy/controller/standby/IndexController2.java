package com.zxy.controller.standby;

import com.zxy.dto.Msg;
import com.zxy.dto.PageDTO;
import com.zxy.mapper.UserMapper;
import com.zxy.model.User;
import com.zxy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

//@Controller
public class IndexController2 {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @ResponseBody
    @GetMapping({"/","/index","/index.html"})
    public Msg index(HttpServletRequest request,
                     @RequestParam(value = "page",defaultValue = "1") Integer page,
                     @RequestParam(value = "size",defaultValue = "5") Integer size){
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.selectByToken(token);
                    if (user != null){
                        request.getSession().setAttribute("githubUser",user);
                    }
                    break;
                }
            }
        }
        PageDTO pageDTO = questionService.selectWithLimit(page,size);
        return Msg.success().add("pageDTO",pageDTO);
    }

}
