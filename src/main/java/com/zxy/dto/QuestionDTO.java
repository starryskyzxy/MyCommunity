package com.zxy.dto;

import com.zxy.model.Question;
import com.zxy.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private Question question;
    private User user;

}
