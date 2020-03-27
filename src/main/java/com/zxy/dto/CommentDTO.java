package com.zxy.dto;

import com.zxy.model.Comment;
import com.zxy.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Comment comment;

    private User user;
}
