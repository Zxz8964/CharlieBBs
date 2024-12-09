package com.itvictorkile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateVoteDTO {
    @NotBlank(message = "投票标题不能为空")
    private String title; // 投票标题

    private String description; // 投票描述

    @NotNull(message = "投票选项不能为空")
    private List<String> options; // 投票选项

    @NotNull(message = "投票截至时间不能为空")
    private String endTime; // 投票截至时间

    @NotNull(message = "参与角色不能为空")
    private List<Long> participantRoleIds; // 参与投票的角色ID
    // 其他必要字段
}