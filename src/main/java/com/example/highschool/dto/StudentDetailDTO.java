package com.example.highschool.dto;

import lombok.Data;

/**
 * 学生详细信息DTO
 */
@Data
public class StudentDetailDTO {
    private Long studentId;
    private String studentNumber;
    private String college;
    private String major;
}
