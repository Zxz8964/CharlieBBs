package com.itvictorkile.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "vote_options")
public class VoteOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vote_id", nullable = false)
    private Vote vote;

    @Column(name = "option_text", nullable = false, length = 255)
    private String optionText;

    @Column(name = "votes_count", nullable = false)
    private Long votesCount = 0L;
}

