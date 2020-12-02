package com.imooc.mall.form;

import javax.validation.constraints.NotBlank;

/**
 * Created on 2020-04-14
 */
public class UserLoginform {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginform{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
