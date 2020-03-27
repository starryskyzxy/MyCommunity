package com.zxy.controller;

import com.zxy.dto.CommentCreateDTO;
import com.zxy.dto.ResultDTO;
import com.zxy.exception.CustomizeErrorCode;
import com.zxy.model.Comment;
import com.zxy.model.User;
import com.zxy.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        System.out.println("拿到的type："+ commentCreateDTO.getType());
        User githubUser = (User) request.getSession().getAttribute("githubUser");
        //在插入之前就要判断是否登录，如果未登录则返回异常信息
        if (githubUser == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(githubUser.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        //如果插入的时候没有发生异常则返回成功
        return ResultDTO.okOf();
    }

}
