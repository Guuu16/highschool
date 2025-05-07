package com.example.highschool.dto;

import lombok.Data;

@Data
public class ProjectReviewDTO {
    private Integer status = 1;
    private String feedback = "";
    private Integer credit = 0;
}
