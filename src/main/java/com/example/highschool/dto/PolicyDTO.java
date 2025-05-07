package com.example.highschool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 政策公告数据传输对象
 */
@Data
@Schema(description = "政策公告DTO")
public class PolicyDTO {
    @Schema(description = "公告ID")
    private Long id;

    @Schema(description = "公告标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @Schema(description = "公告内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    @Schema(description = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime publishedAt;
}
