-- 创建主数据库
CREATE DATABASE bbs_forum;
USE bbs_forum;

-- 用户表
-- 存储用户的基本信息，如用户名、邮箱、密码等
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,  -- 用户名必须唯一
    email VARCHAR(255) NOT NULL UNIQUE,  -- 邮箱必须唯一
    password_hash VARCHAR(255) NOT NULL,  -- 存储加密后的密码
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 用户注册时间
);

-- 用户头像表
-- 存储用户头像的URL和生成的pixel头像标识
CREATE TABLE avatars (
    user_id BIGINT PRIMARY KEY,  -- 与用户表关联
    avatar_url VARCHAR(255),  -- 头像的存储地址
    generated_pixel_avatar BOOLEAN DEFAULT FALSE,  -- 是否为自动生成的pixel头像
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 头像创建时间
);

-- 用户角色表
-- 定义用户角色，如普通用户、管理员等
CREATE TABLE user_roles (
    user_id BIGINT,  -- 与用户表关联
    role VARCHAR(50),  -- 角色名称
    PRIMARY KEY (user_id, role)  -- 复合主键，确保用户与角色的唯一性
);

-- 权限表
-- 定义角色与权限的对应关系
CREATE TABLE permissions (
    role VARCHAR(50),  -- 角色名称
    permission VARCHAR(50),  -- 权限名称
    PRIMARY KEY (role, permission)  -- 复合主键，确保角色与权限的唯一性
);

-- 帖子表
-- 存储帖子信息，如标题、内容等
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,  -- 与用户表关联
    title VARCHAR(255) NOT NULL,  -- 帖子标题
    content TEXT NOT NULL,  -- 帖子内容
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 帖子创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  -- 帖子更新时间
);

-- 评论表
-- 存储评论信息，包括评论的帖子ID和内容
CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,  -- 与帖子表关联
    user_id BIGINT NOT NULL,  -- 与用户表关联
    content TEXT NOT NULL,  -- 评论内容
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 评论创建时间
);

-- 系统通知表
-- 存储系统发送的通知信息
CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,  -- 与用户表关联
    message TEXT NOT NULL,  -- 通知内容
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 通知创建时间
);

-- 私信表
-- 存储用户之间的私信
CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,  -- 发送者用户ID，与用户表关联
    receiver_id BIGINT NOT NULL,  -- 接收者用户ID，与用户表关联
    content TEXT NOT NULL,  -- 私信内容
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 私信发送时间
);

-- 标签表
-- 存储标签信息，用于给帖子分类
CREATE TABLE tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,  -- 标签名称必须唯一
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 标签创建时间
);

-- 帖子标签关联表
-- 处理多对多关系：一个帖子可以有多个标签，一个标签可以关联多个帖子
CREATE TABLE post_tags (
    post_id BIGINT,  -- 与帖子表关联
    tag_id BIGINT,  -- 与标签表关联
    PRIMARY KEY (post_id, tag_id)  -- 复合主键，确保每个帖子和标签的唯一组合
);

-- 板块表
-- 存储论坛板块信息
CREATE TABLE forums (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,  -- 板块名称必须唯一
    description TEXT,  -- 板块描述
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 板块创建时间
);

-- 置顶和精华表
-- 存储帖子是否被置顶或加精的状态
CREATE TABLE top_featured (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,  -- 与帖子表关联
    is_pinned BOOLEAN DEFAULT FALSE,  -- 是否置顶
    is_featured BOOLEAN DEFAULT FALSE,  -- 是否加精
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 设置置顶或加精的时间
);

-- 禁言和封禁表
-- 存储用户的禁言和封禁记录及其理由
CREATE TABLE bans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,  -- 被禁言或封禁的用户ID，与用户表关联
    banned_by BIGINT NOT NULL,  -- 执行操作的管理员ID，与用户表关联
    reason TEXT NOT NULL,  -- 禁言或封禁的理由
    ban_type ENUM('mute', 'ban') NOT NULL,  -- 操作类型：禁言或封禁
    duration INT DEFAULT NULL,  -- 禁言的持续时间（分钟），null表示永久封禁
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 操作时间
);

-- 投票表
-- 存储用户投票信息，用于选举管理员、创建新板块等
CREATE TABLE votes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vote_type ENUM('admin_election', 'new_forum_creation') NOT NULL,  -- 投票类型
    user_id BIGINT NOT NULL,  -- 投票的用户ID，与用户表关联
    target_id BIGINT NOT NULL,  -- 投票目标的ID（例如，投票给哪个用户当管理员，或投票创建哪个新板块）
    vote_result BOOLEAN DEFAULT NULL,  -- 投票结果，null表示尚未公布
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 投票时间
);

-- 投票公示表
-- 存储投票结果和管理员的执行操作，公开展示
CREATE TABLE vote_publications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vote_id BIGINT NOT NULL,  -- 与投票表关联
    action_type ENUM('vote_result', 'ban_mute_action') NOT NULL,  -- 公示的类型：投票结果或封禁/禁言操作
    action_details TEXT NOT NULL,  -- 公示的详细内容
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 公示时间
);
