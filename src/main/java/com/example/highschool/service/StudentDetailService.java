package com.example.highschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.highschool.entity.StudentDetail;

/**
 * 学生详细信息服务接口
 */
public interface StudentDetailService extends IService<StudentDetail> {
    /**
     * 根据学生ID获取详细信息
     * @param studentId 学生ID
     * @return 学生详细信息
     */
    StudentDetail getByStudentId(Long studentId);
    
    /**
     * 更新学生详细信息
     * @param studentDetail 学生详细信息
     * @return 是否更新成功
     */
    boolean updateStudentDetail(StudentDetail studentDetail);
}
