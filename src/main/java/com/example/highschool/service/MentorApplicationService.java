package com.example.highschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.highschool.entity.MentorApplication;

import java.util.List;

/**
 * 导师申请服务接口
 */
public interface MentorApplicationService extends IService<MentorApplication> {

    /**
     * 学生申请导师
     *
     * @param application 申请信息
     * @return 申请结果
     */
    MentorApplication applyMentor(MentorApplication application);

    /**
     * 教师审核导师申请
     *
     * @param id 申请ID
     * @param teacherId 教师ID
     * @param status 审核状态：1-通过，2-拒绝
     * @return 审核结果
     */
    boolean reviewApplication(Long id, Long teacherId, Integer status);

    /**
     * 获取学生的导师申请列表
     *
     * @param studentId 学生ID
     * @return 申请列表
     */
    List<MentorApplication> getStudentApplications(Long studentId);

    /**
     * 获取教师的导师申请列表
     *
     * @param teacherId 教师ID
     * @return 申请列表
     */
    List<MentorApplication> getTeacherApplications(Long teacherId);
}