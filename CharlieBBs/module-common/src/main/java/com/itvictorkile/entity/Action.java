package com.itvictorkile.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action_name", nullable = false, length = 255)
    private String actionName;

    @Column(name = "action_type", nullable = false, length = 255)
    private String actionType;

    @Column(name = "api_endpoint", length = 255)
    private String apiEndpoint;

    @Column(name = "method", nullable = false, length = 10)
    private String method;

    @Column(name = "action_details", columnDefinition = "TEXT")
    private String actionDetails;

    @Column(name = "is_multiple_apis", nullable = false)
    private Boolean isMultipleApis;

// Getters and Setters
}