package com.zxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Msg {

    private String state;

    private Map<String,Object> data = new HashMap<>();

    public static Msg success(){
        Msg result = new Msg();
        result.setState("处理成功");
        return result;
    }

    public static Msg fail(){
        Msg result = new Msg();
        result.setState("处理失败");
        return result;
    }

    public Msg add(String key,Object value){
        this.data.put(key,value);
        return this;
    }
}
