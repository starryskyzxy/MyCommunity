package com.zxy.exception;

/**
 * 异常类型的枚举类
 */
public enum  CustomizeErrorCode implements CustomizeErrorCodeInterface {
    QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不换个试试^_^"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试^_^"),
    SYS_ERROR(2004,"服务器出了点问题，请稍后再试试吧"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或者不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在了"),
    CONTENT_IS_EMPTY(2007,"回复内容不能为空")
    ;

    private String message;

    private Integer code;


    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

}
