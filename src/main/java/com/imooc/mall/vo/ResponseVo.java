package com.imooc.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imooc.mall.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2020-04-14
 */
@Slf4j
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {
    private Integer status;

    private String msg;

    private T data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResponseVo(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ResponseVo<T> successByMsg(String msg){
        log.info("23333");
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),msg);
    }

    public static <T> ResponseVo<T> success(T data){
        log.info("23333");
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),data);
    }

    public static <T> ResponseVo<T> successByMsg(){
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getDesc());
    }


    public static <T> ResponseVo<T> error(ResponseEnum responseEnum,String msg){
        return new ResponseVo<>(responseEnum.getCode(),msg);
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum){
        return new ResponseVo<>(responseEnum.getCode(),responseEnum.getDesc());
    }
}
