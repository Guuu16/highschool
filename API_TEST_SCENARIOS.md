# API测试场景文档

## HelloController
### GET /api/hello
- 正常场景:
  - 描述: 测试基础接口连通性
  - 请求: GET /api/hello
  - 预期响应: 200 OK, "hello world!"

## ProjectController
### POST /api/projects
- 正常场景:
  - 描述: 创建完整信息的项目
  - 请求: 包含所有必填字段
  - 示例请求:
    ```json
    {
      "name": "高中科研项目",
      "description": "这是一个关于...",
      "startDate": "2023-09-01",
      "endDate": "2023-12-31",
      "members": ["user1", "user2"]
    }
    ```
  - 预期响应: 201 Created, 包含项目ID和创建时间

- 边界条件:
  - 描述: 创建只含必填字段的项目
  - 请求: 仅包含name和description
  - 预期响应: 201 Created

- 异常场景:
  - 描述: 缺少必填字段
  - 请求: 缺少name字段
  - 预期响应: 400 Bad Request

### PUT /api/projects/{id}
- 正常场景:
  - 描述: 更新项目信息
  - 请求: PUT /api/projects/project123
  - 示例请求:
    ```json
    {
      "name": "更新后的项目名称",
      "description": "更新后的描述...",
      "status": "IN_PROGRESS"
    }
    ```
  - 预期响应: 200 OK, 包含更新时间和项目ID

- 异常场景:
  - 描述: 更新不存在的项目
  - 请求: PUT /api/projects/invalid-id
  - 预期响应: 404 Not Found

## UserController
### POST /api/user/login
- 正常场景:
  - 描述: 使用正确凭据登录
  - 请求: POST /api/user/login
  - 示例请求:
    ```json
    {
      "username": "student1",
      "password": "password123"
    }
    ```
  - 预期响应: 200 OK, 包含token和用户信息

- 异常场景:
  - 描述: 使用错误密码登录
  - 请求: 正确username但错误password
  - 预期响应: 401 Unauthorized

### POST /api/user/register
- 正常场景:
  - 描述: 注册新用户
  - 请求: POST /api/user/register
  - 示例请求:
    ```json
    {
      "username": "newuser",
      "password": "password123",
      "email": "newuser@example.com",
      "role": "STUDENT"
    }
    ```
  - 预期响应: 201 Created, 包含用户ID和创建时间

- 异常场景:
  - 描述: 注册已存在的用户名
  - 请求: 使用已存在的username
  - 预期响应: 409 Conflict

## MentorApplicationController
### POST /api/mentor-applications
- 正常场景:
  - 描述: 学生申请导师
  - 请求: POST /api/mentor-applications
  - 示例请求:
    ```json
    {
      "projectId": "project123",
      "reason": "需要导师指导项目方向",
      "preferredMentorId": "mentor456"
    }
    ```
  - 预期响应: 201 Created, 包含申请ID和状态

### PUT /api/mentor-applications/{id}/review
- 正常场景:
  - 描述: 教师审核导师申请
  - 请求: PUT /api/mentor-applications/app123/review
  - 示例请求:
    ```json
    {
      "status": "APPROVED",
      "feedback": "申请理由充分，同意指导"
    }
    ```
  - 预期响应: 200 OK, 包含审核时间

## AdminController
### GET /admin/user/list
- 正常场景:
  - 描述: 管理员分页查询用户
  - 请求: GET /admin/user/list?page=1&size=10&role=STUDENT
  - 预期响应: 200 OK, 包含用户列表和总数

### PUT /admin/user/{id}/status
- 正常场景:
  - 描述: 管理员修改用户状态
  - 请求: PUT /admin/user/user123/status
  - 示例请求:
    ```json
    {
      "status": "INACTIVE"
    }
    ```
  - 预期响应: 200 OK, 包含更新时间

### PUT /admin/user/{id}/role
- 正常场景:
  - 描述: 管理员修改用户角色
  - 请求: PUT /admin/user/user123/role
  - 示例请求:
    ```json
    {
      "role": "TEACHER"
    }
    ```
  - 预期响应: 200 OK, 包含更新时间

## StudentController
### POST /student/project
- 正常场景:
  - 描述: 学生创建项目
  - 请求: POST /student/project
  - 示例请求:
    ```json
    {
      "name": "学生科研项目",
      "description": "这是一个学生项目...",
      "startDate": "2023-09-01",
      "endDate": "2023-12-31"
    }
    ```
  - 预期响应: 201 Created, 包含项目ID和创建时间

### GET /student/projects
- 正常场景:
  - 描述: 学生获取项目列表
  - 请求: GET /student/projects?status=ACTIVE&page=1&size=10
  - 预期响应: 200 OK, 包含项目列表

## 测试执行说明
1. 使用Postman或类似工具发送请求
2. 验证响应状态码和内容
3. 记录实际结果与预期结果的差异
4. 对于异常场景，确保返回适当的错误信息