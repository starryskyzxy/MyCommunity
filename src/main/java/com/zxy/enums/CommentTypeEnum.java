package com.zxy.enums;

public enum  CommentTypeEnum {
    QUESTION(1), COMMENT(2);

    private Integer type;

    CommentTypeEnum(Integer type){
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum typeEnum : CommentTypeEnum.values()) {
            if (typeEnum.getType().equals(type)){
                return true;
            }
        }
        return false;
    }

    public Integer getType(){
        return type;
    }
}
