package com.example.highschool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 项目数据传输对象
 */
@Data
@Schema(description = "项目信息DTO")
public class ProjectDTO {
    @Schema(description = "项目ID")
    private Long id;

    @Schema(description = "学生ID")
    @NotNull(message = "学生ID不能为空")
    private Long studentId;

    @Schema(description = "教师ID")
    @NotNull(message = "教师ID不能为空")
    private Long teacherId;

    @Schema(description = "项目类别ID")
    @NotNull(message = "项目类别不能为空")
    private Long categoryId;

    @Schema(description = "项目标题")
    @NotBlank(message = "项目标题不能为空")
    private String title;

    @Schema(description = "项目描述")
    private String description;

    @Schema(description = "项目状态：0-待审核，1-已通过，2-已拒绝")
    private Integer status;

    @Schema(description = "项目学分")
    private Integer credit;

    @Schema(description = "反馈意见")
    private String feedback;

    @Schema(description = "计划文件URL")
    private String planFileUrl;
}
