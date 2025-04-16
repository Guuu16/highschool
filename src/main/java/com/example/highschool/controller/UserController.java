package com.example.highschool.controller;

import com.example.highschool.common.api.Result;
import com.example.highschool.dto.PasswordUpdateDTO;
import com.example.highschool.dto.StudentDetailDTO;
import com.example.highschool.dto.UserLoginDTO;
import com.example.highschool.dto.UserRegisterDTO;
import com.example.highschool.dto.UserUpdateDTO;
import com.example.highschool.entity.StudentDetail;
import com.example.highschool.entity.User;
import com.example.highschool.service.StudentDetailService;
import com.example.highschool.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户登录、注册、获取用户信息等接口")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private StudentDetailService studentDetailService;

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口，返回JWT令牌")
    public Result<Map<String, Object>> login(@RequestBody @Valid UserLoginDTO loginDTO) {
        Map<String, String> loginResult = userService.login(loginDTO.getUsername(), loginDTO.getPassword());
        if (loginResult == null) {
            return Result.validateFailed("用户名或密码错误");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", loginResult.get("token"));
        resultMap.put("tokenHead", "Bearer ");
        resultMap.put("role", loginResult.get("role"));
        return Result.success(resultMap);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public Result<User> register(@RequestBody @Valid UserRegisterDTO registerDTO) {
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);
        User registeredUser = userService.register(user);
        if (registeredUser == null) {
            return Result.failed("注册失败，用户名已存在");
        }
        return Result.success(registeredUser);
    }

    @GetMapping("/me")
    @Operation(summary = "获取当前登录用户信息", description = "获取当前登录用户信息接口")
    public Result<User> getCurrentUser() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return Result.unauthorized();
        }
        // 不返回密码
        currentUser.setPassword(null);
        return Result.success(currentUser);
    }

    @PutMapping("/update")
    @Operation(summary = "更新个人信息", description = "更新当前登录用户的个人信息")
    public Result<User> updateUser(@RequestBody @Valid UserUpdateDTO updateDTO) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return Result.unauthorized();
        }
        
        // 更新允许修改的字段
        currentUser.setRealName(updateDTO.getRealName());
        currentUser.setEmail(updateDTO.getEmail());
        currentUser.setPhone(updateDTO.getPhone());
        
        boolean updated = userService.updateUser(currentUser);
        if (!updated) {
            return Result.failed("更新个人信息失败");
        }
        return Result.success(currentUser);
    }

    @PutMapping("/password")
    @Operation(summary = "修改密码", description = "修改当前登录用户的密码")
    public Result<Void> updatePassword(@RequestBody @Valid PasswordUpdateDTO passwordUpdateDTO) {
        boolean updated = userService.updatePassword(passwordUpdateDTO);
        if (!updated) {
            return Result.failed("修改密码失败，请检查旧密码是否正确或新密码是否一致");
        }
        return Result.success(null);
    }

    @PostMapping("/student-detail")
    @Operation(summary = "创建学生详细信息", description = "创建当前登录用户的学生详细信息")
    public Result<StudentDetail> createStudentDetail(@RequestBody @Valid StudentDetailDTO detailDTO) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return Result.unauthorized();
        }
        
        StudentDetail detail = new StudentDetail();
        BeanUtils.copyProperties(detailDTO, detail);
        detail.setStudentId(currentUser.getId());
        
        boolean saved = studentDetailService.save(detail);
        if (!saved) {
            return Result.failed("创建学生详细信息失败");
        }
        return Result.success(detail);
    }

    @PutMapping("/student-detail")
    @Operation(summary = "更新学生详细信息", description = "更新当前登录用户的学生详细信息")
    public Result<StudentDetail> updateStudentDetail(@RequestBody @Valid StudentDetailDTO detailDTO) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return Result.unauthorized();
        }
        
        StudentDetail existingDetail = studentDetailService.getByStudentId(currentUser.getId());
        if (existingDetail == null) {
            return Result.failed("学生详细信息不存在");
        }
        
        BeanUtils.copyProperties(detailDTO, existingDetail);
        boolean updated = studentDetailService.updateStudentDetail(existingDetail);
        if (!updated) {
            return Result.failed("更新学生详细信息失败");
        }
        return Result.success(existingDetail);
    }

    @GetMapping("/student-detail")
    @Operation(summary = "获取学生详细信息", description = "获取当前登录用户的学生详细信息")
    public Result<StudentDetail> getStudentDetail() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return Result.unauthorized();
        }
        
        StudentDetail detail = studentDetailService.getByStudentId(currentUser.getId());
        if (detail == null) {
            return Result.failed("学生详细信息不存在");
        }
        return Result.success(detail);
    }
}
