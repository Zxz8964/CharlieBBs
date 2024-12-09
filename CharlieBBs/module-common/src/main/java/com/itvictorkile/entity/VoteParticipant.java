package com.itvictorkile.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vote_participants")
public class VoteParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vote_id", nullable = false)
    private Vote vote;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
