# 接口文档

## HelloController
- `GET /api/hello`: 测试接口，返回"hello world!"

## ProjectController
- `POST /api/projects`: 创建项目（学生）
  - 请求参数:
    - name: 项目名称 (string, 必填)
    - description: 项目描述 (string, 必填)
    - startDate: 开始日期 (string, yyyy-MM-dd格式)
    - endDate: 结束日期 (string, yyyy-MM-dd格式)
    - members: 成员ID列表 (array[string])
  - 响应参数:
    - id: 项目ID (string)
    - status: 创建状态 (string)
    - createdAt: 创建时间 (string)
  - 示例:
    ```json
    {
      "name": "高中科研项目",
      "description": "这是一个关于...",
      "startDate": "2023-09-01",
      "endDate": "2023-12-31",
      "members": ["user1", "user2"]
    }
    ```

- `PUT /api/projects/{id}`: 更新项目
  - 请求参数:
    - id: 项目ID (path参数, string, 必填)
    - name: 项目名称 (string, 可选)
    - description: 项目描述 (string, 可选)
    - status: 项目状态 (string, 可选)
  - 响应参数:
    - id: 项目ID (string)
    - updatedAt: 更新时间 (string)
  - 示例:
    ```json
    {
      "name": "更新后的项目名称",
      "description": "更新后的描述...",
      "status": "IN_PROGRESS"
    }
    ```

## UserController
- `POST /api/user/login`: 用户登录
  - 请求参数:
    - username: 用户名 (string, 必填)
    - password: 密码 (string, 必填)
  - 响应参数:
    - token: 认证令牌 (string)
    - userId: 用户ID (string)
    - role: 用户角色 (string)
  - 示例:
    ```json
    {
      "username": "student1",
      "password": "password123"
    }
    ```

- `POST /api/user/register`: 用户注册
  - 请求参数:
    - username: 用户名 (string, 必填)
    - password: 密码 (string, 必填)
    - email: 邮箱 (string, 必填)
    - role: 角色 (string, 可选, 默认STUDENT)
  - 响应参数:
    - id: 用户ID (string)
    - username: 用户名 (string)
    - createdAt: 创建时间 (string)
  - 示例:
    ```json
    {
      "username": "newuser",
      "password": "password123",
      "email": "newuser@example.com",
      "role": "STUDENT"
    }
    ```

## MentorApplicationController
- `POST /api/mentor-applications`: 申请导师（学生）
  - 请求参数:
    - projectId: 项目ID (string, 必填)
    - reason: 申请理由 (string, 必填)
    - preferredMentorId: 首选导师ID (string, 可选)
  - 响应参数:
    - id: 申请ID (string)
    - status: 申请状态 (string)
    - createdAt: 创建时间 (string)
  - 示例:
    ```json
    {
      "projectId": "project123",
      "reason": "需要导师指导项目方向",
      "preferredMentorId": "mentor456"
    }
    ```

- `PUT /api/mentor-applications/{id}/review`: 审核导师申请（教师）
  - 请求参数:
    - id: 申请ID (path参数, string, 必填)
    - status: 审核状态 (string, 必填, APPROVED/REJECTED)
    - feedback: 反馈意见 (string, 可选)
  - 响应参数:
    - id: 申请ID (string)
    - reviewedAt: 审核时间 (string)
  - 示例:
    ```json
    {
      "status": "APPROVED",
      "feedback": "申请理由充分，同意指导"
    }
    ```

## AdminController
- `GET /admin/user/list`: 分页查询用户（管理员）
  - 请求参数:
    - page: 页码 (integer, 可选, 默认1)
    - size: 每页数量 (integer, 可选, 默认10)
    - role: 角色过滤 (string, 可选)
  - 响应参数:
    - users: 用户列表 (array[object])
      - id: 用户ID (string)
      - username: 用户名 (string)
      - role: 角色 (string)
      - status: 状态 (string)
    - total: 总数量 (integer)
  - 示例:
    ```json
    {
      "page": 1,
      "size": 10,
      "role": "STUDENT"
    }
    ```

- `PUT /admin/user/{id}/status`: 修改用户状态（管理员）
  - 请求参数:
    - id: 用户ID (path参数, string, 必填)
    - status: 新状态 (string, 必填, ACTIVE/INACTIVE)
  - 响应参数:
    - id: 用户ID (string)
    - updatedAt: 更新时间 (string)
  - 示例:
    ```json
    {
      "status": "INACTIVE"
    }
    ```

- `PUT /admin/user/{id}/role`: 修改用户角色（管理员）
  - 请求参数:
    - id: 用户ID (path参数, string, 必填)
    - role: 新角色 (string, 必填, ADMIN/TEACHER/STUDENT)
  - 响应参数:
    - id: 用户ID (string)
    - updatedAt: 更新时间 (string)
  - 示例:
    ```json
    {
      "role": "TEACHER"
    }
    ```

## StudentController
- `POST /student/project`: 创建项目（学生）
  - 请求参数:
    - name: 项目名称 (string, 必填)
    - description: 项目描述 (string, 必填)
    - startDate: 开始日期 (string, yyyy-MM-dd格式)
    - endDate: 结束日期 (string, yyyy-MM-dd格式)
  - 响应参数:
    - id: 项目ID (string)
    - status: 创建状态 (string)
    - createdAt: 创建时间 (string)
  - 示例:
    ```json
    {
      "name": "学生科研项目",
      "description": "这是一个学生项目...",
      "startDate": "2023-09-01",
      "endDate": "2023-12-31"
    }
    ```

- `GET /student/projects`: 获取学生项目列表
  - 请求参数:
    - status: 项目状态过滤 (string, 可选)
    - page: 页码 (integer, 可选, 默认1)
    - size: 每页数量 (integer, 可选, 默认10)
  - 响应参数:
    - projects: 项目列表 (array[object])
      - id: 项目ID (string)
      - name: 项目名称 (string)
      - status: 项目状态 (string)
      - createdAt: 创建时间 (string)
    - total: 总数量 (integer)
  - 示例:
    ```json
    {
      "status": "IN_PROGRESS",
      "page": 1,
      "size": 10
    }
    ```

## EventController
- `POST /api/events`: 创建讲座/培训信息（教师/管理员）
  - 请求参数:
    - title: 讲座标题 (string, 必填)
    - description: 讲座描述 (string, 必填)
    - startTime: 开始时间 (string, yyyy-MM-dd HH:mm格式)
    - endTime: 结束时间 (string, yyyy-MM-dd HH:mm格式)
    - location: 地点 (string, 必填)
    - maxParticipants: 最大参与人数 (integer, 可选)
  - 响应参数:
    - id: 事件ID (string)
    - status: 创建状态 (string)
    - createdAt: 创建时间 (string)
  - 示例:
    ```json
    {
      "title": "科研方法培训",
      "description": "介绍科研基本方法...",
      "startTime": "2023-10-15 14:00",
      "endTime": "2023-10-15 16:00",
      "location": "教学楼101",
      "maxParticipants": 50
    }
    ```

## TeacherController
- `GET /teacher/projects`: 获取教师指导的项目
  - 请求参数:
    - status: 项目状态过滤 (string, 可选)
    - page: 页码 (integer, 可选, 默认1)
    - size: 每页数量 (integer, 可选, 默认10)
  - 响应参数:
    - projects: 项目列表 (array[object])
      - id: 项目ID (string)
      - name: 项目名称 (string)
      - status: 项目状态 (string)
      - createdAt: 创建时间 (string)
    - total: 总数量 (integer)
  - 示例:
    ```json
    {
      "status": "PENDING_REVIEW",
      "page": 1,
      "size": 10
    }
    ```

- `PUT /teacher/project/{id}/review`: 审核项目（教师）
  - 请求参数:
    - id: 项目ID (path参数, string, 必填)
    - status: 审核状态 (string, 必填, APPROVED/REJECTED)
    - feedback: 反馈意见 (string, 可选)
  - 响应参数:
    - id: 项目ID (string)
    - reviewedAt: 审核时间 (string)
  - 示例:
    ```json
    {
      "status": "APPROVED",
      "feedback": "项目计划完善，同意立项"
    }
    ```

## PolicyController
  - `POST /api/policies`: 创建政策公告（管理员）
    - 请求参数:
      - title: 公告标题 (string, 必填)
      - content: 公告内容 (string, 必填)
      - isImportant: 是否重要 (boolean, 可选, 默认false)
    - 响应参数:
      - id: 公告ID (string)
      - createdAt: 创建时间 (string)
    - 示例:
      ```json
      {
        "title": "科研项目申报指南",
        "content": "请按照以下要求...",
        "isImportant": true
      }
      ```

## MessageController
- `POST /api/messages`: 发送消息
  - 请求参数:
    - receiverId: 接收者ID (string, 必填)
    - content: 消息内容 (string, 必填)
    - title: 消息标题 (string, 可选)
  - 响应参数:
    - id: 消息ID (string)
    - sentAt: 发送时间 (string)
  - 示例:
    ```json
    {
      "receiverId": "user123",
      "content": "关于项目进度的讨论",
      "title": "项目讨论"
    }
    ```

- `GET /api/messages/received`: 获取收到的消息
  - 请求参数:
    - page: 页码 (integer, 可选, 默认1)
    - size: 每页数量 (integer, 可选, 默认10)
    - unreadOnly: 仅未读消息 (boolean, 可选, 默认false)
  - 响应参数:
    - messages: 消息列表 (array[object])
      - id: 消息ID (string)
      - senderId: 发送者ID (string)
      - title: 消息标题 (string)
      - content: 消息内容 (string)
      - isRead: 是否已读 (boolean)
      - sentAt: 发送时间 (string)
    - total: 总数量 (integer)
  - 示例:
    ```json
    {
      "page": 1,
      "size": 10,
      "unreadOnly": true
    }
    ```

## ProjectProgressController
- `POST /api/project-progress`: 创建项目进度（学生）
  - 请求参数:
    - projectId: 项目ID (string, 必填)
    - title: 进度标题 (string, 必填)
    - content: 进度内容 (string, 必填)
    - percentage: 完成百分比 (integer, 可选)
  - 响应参数:
    - id: 进度ID (string)
    - createdAt: 创建时间 (string)
  - 示例:
    ```json
    {
      "projectId": "project123",
      "title": "第一阶段完成",
      "content": "已完成文献综述和实验设计",
      "percentage": 30
    }
    ```