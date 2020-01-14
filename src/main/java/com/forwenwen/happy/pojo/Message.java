package com.forwenwen.happy.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.forwenwen.happy.util.MessageCode;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Data
public class Message {

    private int status;//返回ID
    private String info;//返回错误信息
    private Object data;//返回类型参数

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String url;//返回跳转网页

    @JsonIgnore
    private HttpServletRequest request;

    public Message(HttpServletRequest request){
        this.request = request;
    }

    public Object toBody(){
        if(data == null ){data = new HashMap<String,Object>();}

        if (request.getHeader("X-Requested-With")!=null && !request.getHeader("X-Requested-With").equals("") && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
            return this;
        }else {
            ModelAndView mv = new ModelAndView();
            mv.addObject("message", this);
            mv.setViewName("page/info/redirect");
            return mv;
        }
    }

    public Object setSuccess(String info, Object data){
        setStatus(MessageCode.SUCCESS);
        setData(data);
        setInfo(info);
        return this.toBody();
    }

    public Object setSuccess(String info){
        setStatus(MessageCode.SUCCESS);
        setData(null);
        setInfo(info);
        return this.toBody();
    }

    public Object setSuccess(String info, Object data,String url){
        setStatus(MessageCode.SUCCESS);
        setData(data);
        setInfo(info);
        setUrl(url);
        return this.toBody();
    }


    public Object setError(String info, Object data,String url){
        setStatus(MessageCode.ERROR);
        setData(data);
        setInfo(info);
        setUrl(url);
        return this.toBody();
    }

    public Object setError(String info, Object data){
        setStatus(MessageCode.ERROR);
        setData(data);
        setInfo(info);
        return this.toBody();
    }


    public Object setError(String info){
        setStatus(MessageCode.ERROR);
        setData(null);
        setInfo(info);
        return this.toBody();
    }

    public Object setAjaxReturn(String info, Object data,int messageCode){
        setStatus(messageCode);
        setData(data);
        setInfo(info);
        return this;
    }



}

