 # Charlie BBS - 分布式论坛系统(开发中)

## 项目概述

Charlie BBS 是一个基于微服务架构的分布式论坛系统，提供完整的社区讨论、用户管理、内容管理等功能。系统采用现代化的技术栈，实现高可用、高性能、可扩展的论坛平台。

## 技术栈

### 后端

* Spring Boot 3
* Spring Cloud
* Spring Security
* Spring Data JPA
* JWT
* MySQL
* Redis
* RabbitMQ/Kafka

### 前端

* Vue.js 3
* Vuex
* Vue Router
* Element Plus

## 项目结构

    charlie-bbs/
    ├── api-gateway/           # API网关服务
    ├── user-service/         # 用户服务（已完成）
    ├── post-service/         # 帖子服务（已完成）
    ├── board-service/        # 板块服务（开发中）
    ├── chat-service/         # 聊天服务（开发中）
    ├── notification-service/ # 通知服务（开发中）
    ├── report-service/       # 举报服务（开发中）
    ├── permission-service/   # 权限服务（已完成）
    ├── analytics-service/    # 统计服务（待开发）
    └── security-service/     # 安全服务（待开发）

## 已完成功能

### 用户服务 (user-service)

* [x] 用户注册与登录
* [x] JWT认证
* [x] 角色与权限管理
* [x] 用户信息管理
* [ ] 二步验证
* [x] 邮箱验证

### 帖子服务 (post-service)

* [x] 发帖功能
* [x] 评论系统
* [x] 点赞/点踩
* [ ] 内容审核

### 板块服务 (board-service)

* [x] 板块管理
* [x] 公告系统
* [x] 权限控制

### 聊天服务 (chat-service)

* [ ] 实时聊天
* [x] 私信系统
* [ ] 文件共享

...

## 如何启动

### 环境要求

* JDK 11+
* Maven 3.6+
* MySQL 8.0+
* Redis 6.0+
* Node.js 16+

### 后端服务启动

1. 克隆仓库
  
      git clone https://github.com/your-username/charlie-bbs.git
  
2. 配置数据库
  
      # 执行数据库脚本
      mysql -u root -p < database/init.sql
  
3. 修改配置文件
  
      # 修改各服务的application.yml，配置数据库连接等信息
  
4. 启动服务（待完成）
  
      cd charlie-bbs
      mvn clean install
      java -jar user-service/target/user-service.jar
  

### 前端启动

    cd frontend
    npm install
    npm run serve

## 开发规范

### Git提交规范

* feat: 新功能
* fix: 修复bug
* docs: 文档更新
* style: 代码格式调整
* refactor: 重构
* test: 测试相关
* chore: 构建/工具链相关

### 代码规范

* 使用Lombok简化代码
* 控制器统一使用@RestController注解
* 所有接口都需要添加Swagger文档注释

## 贡献

欢迎提交Pull Request或Issue。

## 许可证

MIT License
