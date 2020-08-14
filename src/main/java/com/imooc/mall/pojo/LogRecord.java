package com.imooc.mall.pojo;

public class LogRecord {
    private Integer id;

    private String operator;

    private Long operatetime;

    private Integer optype;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Long getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(Long operatetime) {
        this.operatetime = operatetime;
    }

    public Integer getOptype() {
        return optype;
    }

    public void setOptype(Integer optype) {
        this.optype = optype;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}