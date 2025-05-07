package com.example.highschool.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户信息更新DTO
 */
@Data
public class UserUpdateDTO {
    @NotBlank(message = "真实姓名不能为空")
    private String realName;
    
    private String email;
    
    private String phone;

    public String getRealName() {
        return realName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
