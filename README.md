# 创新创业项目管理系统

## 环境信息

### 系统信息
- 操作系统: macOS
- 默认Shell: /bin/zsh
- 工作目录: /Users/gujie/Documents/code/highschool

### 项目结构
```
highschool/
├── src/
│   ├── main/
│   │   ├── java/com/example/highschool/
│   │   │   ├── controller/   # 控制器
│   │   │   ├── service/      # 服务层
│   │   │   ├── entity/       # 实体类
│   │   │   ├── dto/          # 数据传输对象
│   │   │   └── config/       # 配置类
│   │   └── resources/        # 资源文件
│   └── test/                 # 测试代码
├── target/                   # 编译输出
├── mvnw                      # Maven包装器
├── pom.xml                   # Maven配置
└── README.md                 # 项目文档
```

### 技术栈
- 后端: Spring Boot 3.x, MyBatis-Plus
- 安全: Spring Security, JWT
- 数据库: MySQL
- 构建工具: Maven

### 运行要求
- JDK 17+
- MySQL 8.0+
- Maven 3.8+

### API文档
- Swagger UI: http://localhost:8090/swagger-ui.html
- 默认端口: 8090
