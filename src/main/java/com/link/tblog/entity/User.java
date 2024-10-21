package com.link.tblog.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class User {

    private Long id;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "用户名称不能为空")
    private String userName;

    @NotBlank(message = "用户密码不能为空")
    private String passWord;

    private String salt;

    @TableLogic
    private Integer deleted;

    private Date createTime;

    private Date updateTime;

    public User() {}

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public User(String userName, String email, String passWord) {
        this.email = email;
        this.userName = userName;
        this.passWord = passWord;
    }
}
