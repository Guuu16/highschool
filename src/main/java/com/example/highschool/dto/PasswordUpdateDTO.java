package com.example.highschool.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 修改密码数据传输对象
 */
@Data
public class PasswordUpdateDTO {
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;
    
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
    
    @NotEmpty(message = "确认密码不能为空")
    private String confirmPassword;
}
