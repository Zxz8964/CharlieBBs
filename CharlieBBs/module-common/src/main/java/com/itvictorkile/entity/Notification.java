package com.itvictorkile.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String type; // 通知类型

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message; // 通知内容

    @Column(length = 255)
    private String link; // 通知的链接地址

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false; // 是否已读

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    public void setSender(User user) {
    }
}
