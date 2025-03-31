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
}
