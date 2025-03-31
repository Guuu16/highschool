# 创新创业项目管理系统

## 数据库初始化

系统使用MySQL数据库，在启动项目前需要先创建数据库并初始化表结构。

### 步骤1：创建数据库

```sql
CREATE DATABASE innovation_project DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

### 步骤2：初始化表结构

执行项目中的SQL脚本文件：`src/main/resources/db/schema.sql`

## 项目结构说明

### 实体类

系统包含以下实体类：

- `User`: 用户实体，包括管理员、教师和学生
- `Project`: 创新创业项目
- `Category`: 项目类别
- `ProjectProgress`: 项目进度
- `Message`: 师生沟通消息
- `Event`: 讲座/培训信息
- `Policy`: 政策公告
- `MentorApplication`: 学生导师申请

### 数据库表

- `user`: 用户表
- `project`: 创新创业项目表
- `category`: 项目类别表
- `project_progress`: 项目进度表
- `message`: 师生沟通表
- `event`: 讲座/培训信息表
- `policy`: 政策公告表
- `mentor_application`: 学生导师申请表

## 启动项目

1. 确保MySQL服务已启动，并已创建数据库和表结构
2. 配置`application.properties`中的数据库连接信息
3. 运行`HighschoolApplication.java`启动项目
4. 访问 http://localhost:8090/api/swagger-ui.html 查看API文档

## 下一步开发

完成数据库表结构创建后，下一步将进行：

1. 各模块的Service层开发
2. Controller接口开发
3. 前端页面开发