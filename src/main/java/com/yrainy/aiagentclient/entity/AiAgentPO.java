package com.yrainy.aiagentclient.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Data
@Accessors(chain = true)
public class AiAgentPO {

    private String agentId;

    private String switchStatus;

    private String url;

    private String token;

    private String agentPlatform;


}
