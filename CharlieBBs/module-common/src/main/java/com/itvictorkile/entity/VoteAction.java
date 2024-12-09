package com.itvictorkile.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vote_actions")
public class VoteAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vote_id", nullable = false)
    private Vote vote;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false)
    private Action action;
}
