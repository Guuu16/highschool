package com.example.highschool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户状态DTO
 */
@Data
@Schema(description = "用户状态DTO")
public class UserStatusDTO {
    @Schema(description = "用户ID", required = true)
    private Long id;
    
    @Schema(description = "状态：0-禁用，1-启用", required = true)
    private Integer status;
}
