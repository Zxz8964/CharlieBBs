package com.itvictorkile.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    //getter and setter
}
