package com.zxy.controller;

import com.zxy.dto.Msg;
import com.zxy.dto.PageDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestParam("parentId") Long p){

    }

}
