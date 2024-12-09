package com.itvictorkile.dto;

import lombok.Data;

@Data
public class ActionDTO {
    private Long id;
    private String actionName;
    private String actionType;
    private String apiEndpoint;
    private String method;
    private String actionDetails;
    private Boolean isMultipleApis;
}

