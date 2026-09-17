package com.yrainy.aiagentclient.entity.dto;

import lombok.Data;

/**
 * HiAgent响应
 *
 * @author zhanglun
 * @since 2026-09-13
 */
@Data
public class HiAgentResponse {

    private String runId;

    private String status;

    private String output;

    private Long costMs;

    private Long costToken;

    public boolean isSuccess() {
        return "success".equals(status);
    }
}
