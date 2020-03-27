package com.zxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateDTO {
    private Long parentId; //父问题的id
    private String content; //回复内容
    private Integer type; //回复的类型
}
