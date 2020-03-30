package com.zxy.mapper;

import com.zxy.model.Comment;
import com.zxy.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentExtMapper {

    void incCommentCount(Comment comment);

    int CommentCount(User user);
}
