package com.zxy.advice;

import com.alibaba.fastjson.JSON;
import com.zxy.dto.ResultDTO;
import com.zxy.exception.CustomizeErrorCode;
import com.zxy.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//全局异常处理，这是一个增强的@controller
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class) //只要没有@responsebody或者@restcontroller都是返回modelandview
    ModelAndView customException(HttpServletRequest request, HttpServletResponse response, Throwable e, Model model) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            ResultDTO resultDTO;
            //返回json
            if (e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                /*
                因为不能在一个方法中返回modelandview和json字符串,所以就只有通过最原始的
                response.getWriter()来向前端返回json数据
                 */
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioexception) {
            }
            return null;
        }else {
            //错误页面跳转
            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }

    }

}
