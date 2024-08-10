-- 用户数据
CREATE DATABASE user_db_shard1;  -- 存储用户ID范围1-1000的数据
CREATE DATABASE user_db_shard2;  -- 存储用户ID范围1001-2000的数据

-- 帖子数据
CREATE DATABASE post_db_shard1;  -- 存储帖子ID范围1-1000的数据
CREATE DATABASE post_db_shard2;  -- 存储帖子ID范围1001-2000的数据

-- 评论数据
CREATE DATABASE comment_db_shard1;  -- 存储评论ID范围1-1000的数据
CREATE DATABASE comment_db_shard2;  -- 存储评论ID范围1001-2000的数据

-- 系统通知数据
CREATE DATABASE notification_db;

-- 私信数据
CREATE DATABASE message_db;

-- 标签数据
CREATE DATABASE tag_db;

-- 板块数据
CREATE DATABASE forum_db;

-- 置顶和精华数据
CREATE DATABASE top_featured_db;

-- 用户头像数据
CREATE DATABASE avatar_db;

-- 日志数据
CREATE DATABASE logs_db; -- 存储系统的访问日志和审计日志

-- 缓存数据
CREATE DATABASE cache_db; -- 存储缓存数据

-- 用户表（分库分表示例，分片管理在应用层进行）
USE user_db_shard1;
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

USE user_db_shard2;
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 帖子表（分库分表示例）
USE post_db_shard1;
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

USE post_db_shard2;
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 评论表（分库分表示例）
USE comment_db_shard1;
CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

USE comment_db_shard2;
CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 系统通知表
USE notification_db;
CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 私信表
USE message_db;
CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 标签表
USE tag_db;
CREATE TABLE tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 板块表
USE forum_db;
CREATE TABLE forums (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 置顶和精华表
USE top_featured_db;
CREATE TABLE top_featured (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    is_pinned BOOLEAN DEFAULT FALSE,
    is_featured BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户头像表
USE avatar_db;
CREATE TABLE avatars (
    user_id BIGINT PRIMARY KEY,
    avatar_url VARCHAR(255),
    generated_pixel_avatar BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户角色和权限管理表
USE user_db_shard1; -- 权限管理可以放在用户相关数据库中
CREATE TABLE user_roles (
    user_id BIGINT,
    role VARCHAR(50),
    PRIMARY KEY (user_id, role)
);

CREATE TABLE permissions (
    role VARCHAR(50),
    permission VARCHAR(50),
    PRIMARY KEY (role, permission)
);

-- 创建审计日志表，用于记录系统中的重要操作
USE logs_db;
CREATE TABLE audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,          -- 执行操作的用户ID
    action VARCHAR(255) NOT NULL,     -- 操作类型，如CREATE、UPDATE、DELETE等
    resource VARCHAR(255) NOT NULL,   -- 操作的资源类型，如用户、帖子等
    resource_id BIGINT NOT NULL,      -- 操作的资源ID
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 操作时间
    details TEXT                      -- 操作详情，可选字段
);

-- 创建访问日志表，用于记录用户和管理员的登录和操作行为
CREATE TABLE access_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,          -- 用户ID
    ip_address VARCHAR(45) NOT NULL,  -- 用户的IP地址
    action VARCHAR(255) NOT NULL,     -- 行为类型，如LOGIN、LOGOUT等
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 行为时间
    user_agent TEXT                   -- 用户代理信息
);

-- 创建缓存表，用于存储热点数据
USE cache_db;
CREATE TABLE cache (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    key_name VARCHAR(255) NOT NULL UNIQUE, -- 缓存的键名称
    value TEXT NOT NULL,                   -- 缓存的值
    expires_at TIMESTAMP,                  -- 缓存的过期时间
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建封禁和禁言表，记录用户的封禁与禁言操作，包括理由和公开信息
USE user_db_shard1;
CREATE TABLE bans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,              -- 被封禁或禁言的用户ID
    admin_id BIGINT NOT NULL,             -- 执行操作的管理员ID
    reason TEXT NOT NULL,                 -- 封禁或禁言的理由
    is_banned BOOLEAN DEFAULT FALSE,      -- 是否封禁
    ban_expires_at TIMESTAMP,             -- 封禁到期时间（如永久封禁则为NULL）
    is_muted BOOLEAN DEFAULT FALSE,       -- 是否禁言
    mute_expires_at TIMESTAMP,            -- 禁言到期时间
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_public BOOLEAN DEFAULT TRUE        -- 是否公开此操作
);

-- 创建投票表，用于管理员选举、板块创建等投票操作，结果公开
USE forum_db;
CREATE TABLE votes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    topic VARCHAR(255) NOT NULL,          -- 投票主题，如“选举管理员”或“创建新板块”
    options TEXT NOT NULL,                -- 投票选项，存储为JSON格式
    votes JSON NOT NULL,                  -- 投票结果，存储为JSON格式
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_public BOOLEAN DEFAULT TRUE        -- 是否公开投票结果
);

-- 用户投票记录表，确保每个用户只能投票一次
CREATE TABLE user_votes (
    vote_id BIGINT,
    user_id BIGINT,
    selected_option VARCHAR(255),         -- 用户选择的选项
    voted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (vote_id, user_id)
);
